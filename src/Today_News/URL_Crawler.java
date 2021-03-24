package Today_News;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URL_Crawler{

	
	static String[][] sid2 = { //Sid2 value
			{"264","265","268","266","267","269"}, //정치
			{"259","258","261","771","260","262","310","263"}, //경제
			{"249","250","251","254","252","59b","255","256","276","257"}, //사회
			{"241","239","240","267","238","376","242","243","244","248","245"}, //생활문화
			{"231","232","233","234","322"}, //세계
			{"731","226","227","230","732","283","229","228"}, //IT과학
	};
		
	
	public ArrayList<Article_Class> select_sid2Num(int sid1) throws IOException{
		
		ArrayList<Article_Class> Article_ArrayList = new ArrayList<Article_Class>(); //return 될 sid2 1개 객체
		
		Article_Crawler article_crawler = new Article_Crawler();
		for(int sid2_idx = 0; /*sid2_idx < sid2[sid1%100].length*/sid2_idx <1; sid2_idx++) { //sid2 loop
			
			
//			System.out.println(/*sid2[sid1%100][sid2_idx]*/"252");
			String final_page_num = final_page(sid1,/*sid2[sid1%100][sid2_idx]*/"252"); //get Final Page number
			
			Page_loop: for(int page_num = 1; page_num < Integer.parseInt(final_page_num); page_num++) { //Page loop
				
				Article_Class article_class = new Article_Class();	//sid2 하위 1개 기사
				
				String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
						+ "sid2=" + /*sid2[sid1%100][sid2_idx]*/"252"
						+ "&sid1=" + sid1
//						+ "&date="
						+ "&page="+page_num;
				
				
				Document doc = Jsoup.connect(URL).get();
				
				Elements Article_List_URL = doc.select("div.content div.list_body ul li dl"); //Aticle URL
				
				for(Element element : Article_List_URL) { //Article List loop
					
					if(Check_Upload_Time(element.toString().split("<span class=\"date")[1].split(">")[1])) {break Page_loop; } //Upload Time > 6 => break Page loop
					
					ArrayList<String> Article_Data = article_crawler.article_crawling(element.toString().split("href=\"")[1].split("\">")[0].replace("&amp;","&")); //Crawling to Article
					article_class.setSid2(/*sid2[sid1%100][sid2_idx]*/"252"); //Store sid2 number
					article_class.setURL(Article_Data.get(0)); //Store URL
					article_class.setTitle(Article_Data.get(1)); //Store Title
					article_class.setTime(Article_Data.get(2)); //Store Time
//					article_class.setContent(Article_Data.get(3)); //Store Content -> 3줄요약 class추가해서 해당 메소드로 content내용 수정해야함
					
					Title_Analysis title_analysis = new Title_Analysis(); //Extract Keyword
					article_class.setKeyword(title_analysis.Text_Analysis(article_class.getTitle())); //Store Keyword
					
					Article_ArrayList.add(article_class); //add article_class from article_arraylist
//					Save_File(Article_Data);
				}
			}
		}
		return Article_ArrayList;
	}
	
	public String final_page(int sid1,String sid2) throws IOException { //get last Page number
		
		String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
				+ "sid2=" + sid2
				+ "&sid1=" + sid1
//				+ "&date="
				+ "&page="+100000; //Over Page Number
		
		Document doc = Jsoup.connect(URL).get();
		Elements elements_nextPage = doc.select("div.content div.paging strong");
				
		return elements_nextPage.toString().split("<strong>")[1].split("</strong>")[0]; //return last page number
	}
	
	public void Save_File(List<String> Article_Data) throws IOException{//Save Text to Article //No use
		
		File Article_Data_File = new File("Article_Data.txt");
		
		BufferedWriter filewriter = new BufferedWriter(new FileWriter(Article_Data_File,true));
		
		for(String data : Article_Data) {
			filewriter.write(data+"|");
		}
		filewriter.write("\n");
		filewriter.flush();
		filewriter.close();
		
	}
	
	public boolean Check_Upload_Time(String Upload_Time) { //6시간전 기사 확인
		
		if(Upload_Time.contains("분")) {
//			System.out.println("OK");
			return false;
		}
		else if(Upload_Time.contains("시간")) {
			if(7>Integer.parseInt(Upload_Time.split("시간")[0])) {
//				System.out.println("OK");
				return false;
			}
		}
//		System.out.println("NO");
		return true;
	}
}
