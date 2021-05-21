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

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class URL_Crawler{

	
	static String[][] sid2 = { //Sid2 value
			{"264","265","268","266","267","269"}, //정치
			{"259","258","261","771","260","262","310","263"}, //경제
			{"249","250","251","254","252","59b","255","256","276","257"}, //사회
			{"241","239","240","267","238","376","242","243","244","248","245"}, //생활문화
			{"231","232","233","234","322"}, //세계
			{"731","226","227","230","732","283","229","228"}, //IT과학
	};
		
	
	public ArrayList<Article_Class> select_sid2Num(int sid1, ArrayList<TextRank_Class> Keyword_List) throws Exception{
		
		System.out.println("sid1-\t\t"+sid1); //check Sid1
		ArrayList<Article_Class> Article_ArrayList = new ArrayList<Article_Class>(); //return 될 sid2 1개 객체
		
		Article_Crawler article_crawler = new Article_Crawler();
		for(int sid2_idx = 0; sid2_idx < sid2[sid1%100].length; sid2_idx++) { //sid2 loop
			System.out.println("sid2-\t"+sid2[sid1%100][sid2_idx]); //check Sid2
			int final_page_num = final_page(sid1,sid2[sid1%100][sid2_idx]/*"252"*/); //get Final Page number
			Page_loop: for(int page_num = 1; page_num < final_page_num; page_num++) { //Page loop
				System.out.println("["+page_num+"/"+final_page_num+"]"); //check page/final page
				
				String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
						+ "sid2=" + sid2[sid1%100][sid2_idx]/*"252"*/
						+ "&sid1=" + sid1
//						+ "&date="
						+ "&page="+page_num;
				
				
				Document doc = Jsoup.connect(URL).get(); //get page document
				
				Elements Article_List_URL = doc.select("div.content div.list_body ul li dl"); //get Article list URL
				
				for(Element element : Article_List_URL) { //Article List loop
					
					if(Check_Upload_Time(element.toString().split("<span class=\"date")[1].split(">")[1])) {break Page_loop; } //Upload Time > 1hour => break Page loop
					Article_Class article_class = new Article_Class();	//sid2 하위 1개 기사
					ArrayList<String> Article_Data = article_crawler.article_crawling(element.toString().split("href=\"")[1].split("\">")[0].replace("&amp;","&")); //Crawling to Article
					if(!Article_Data.get(0).equals("Non")&&!Article_Data.get(1).equals("Non")&&!Article_Data.get(2).equals("Non")) { //Non-exception Data
						
						article_class.setArticle_Sidnum(Integer.toString(sid1)/*sid2[sid1%100][sid2_idx]*/); //Store Sidnum
						article_class.setArticle_URL(Article_Data.get(0)); //Store URL
						article_class.setArticle_Title(Article_Data.get(1)); //Store Title
						System.out.println(sid2[sid1%100][sid2_idx]+"- "+Article_Data.get(1)); //check Sid2 + Title
						article_class.setArticle_Time(Article_Data.get(2)); //Store Time
						article_class.setArticle_Content(Article_Data.get(3)); //Store Content -> 3줄요약 class추가해서 해당 메소드로 content내용 수정해야함 -> Article_Crawler에서 완료
						
						//Keyword//
						article_class.setArticle_Keyword(Keyword_Expect(article_class.getArticle_Title())); //Store Keyword
						ArrayList<String> Title_Keywords = Keyword_Expect(article_class.getArticle_Title());
						for(int keyword_Count=0; keyword_Count < Title_Keywords.size(); keyword_Count++) {
							TextRanking(Keyword_List, Title_Keywords.get(keyword_Count)); //title keyword count
						}
						
						Article_ArrayList.add(article_class); //add article_class from article_arraylist
					}else {
						System.out.println("Error - "+Article_Data.get(0));
					}
					
				}
			}
		}
		return Article_ArrayList;
	}
	
	//Method//
	public int final_page(int sid1,String sid2) throws IOException { //get last Page number
		
		String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
				+ "sid2=" + sid2
				+ "&sid1=" + sid1
//				+ "&date="
				+ "&page="+100000; //Over Page Number
		
		Document doc = Jsoup.connect(URL).get();
		Elements elements_nextPage = doc.select("div.content div.paging strong");
		
		if(Math.floor(Float.parseFloat(elements_nextPage.toString().split("<strong>")[1].split("</strong>")[0])*0.05)>1) {
			return (int)Math.floor(Float.parseFloat(elements_nextPage.toString().split("<strong>")[1].split("</strong>")[0])*0.05); //return last page number
		}
		else {return 2;}
	}
	
	public boolean Check_Upload_Time(String Upload_Time) { //N시간전 기사 확인
		
		if(Upload_Time.contains("분")) {
			return false;
		}
		else if(Upload_Time.contains("시간")) {
//			if(2>Integer.parseInt(Upload_Time.split("시간")[0])) {
//				return false;
//			}
			return true;
		}
		return true;
	}
	
	public void TextRanking(ArrayList<TextRank_Class> Keyword_List,String keyword) { //Keyword Back of Words
		
		boolean newKeyword=true;
		
		for(int i = 0; i < Keyword_List.size(); i++) { //compare keyword list to keyword
				
			if(Keyword_List.get(i).getKeyword().equals(keyword)) { //List's keyword
				Keyword_List.get(i).addKeyword_Rank();
				newKeyword = false;
				break;
			}
		}
		if(newKeyword) { //New Keyword
			TextRank_Class textrank_class = new TextRank_Class(keyword);
			Keyword_List.add(textrank_class);
		}
	}
	
	public ArrayList<String> Keyword_Expect(String Content_Text) { //Extract keyword from text
		
	    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); //Make KOMORAN Object of WiKi model
	
	    KomoranResult analyzeResultList = komoran.analyze(Content_Text); //Analysis Text -> return Keyword List
	    List<Token> tokenList = analyzeResultList.getTokenList(); //Store Keywords to List
	    ArrayList<String> keyWord = new ArrayList<String>();
	    
	    for (Token token : tokenList) { 
	        if(token.getPos().equals("NNP")) { //Extract NNP(고유명사) keyword
	        	if(token.getMorph().length()!=1) {
	        		keyWord.add(token.getMorph()); //Append Keyword
	        	}
	        }        
        }
	    return keyWord;
    }
}
