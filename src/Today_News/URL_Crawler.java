package Today_News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class URL_Crawler {

	
	static String[][] sid2 = {
			{"264","265","268","266","267","269"}, //정치
			{"259","258","261","771","260","262","310","263"}, //경제
			{"249","250","251","254","252","59b","255","256","276","257"}, //사회
			{"241","239","240","267","238","376","242","243","244","248","245"}, //생활문화
			{"231","232","233","234","322"}, //세계
			{"731","226","227","230","732","283","229","228"}, //IT과학
	};
		
	
	
	public void select_sid2Num(int sid1) throws IOException {
		
		Article_Crawler article_crawler = new Article_Crawler();
		
		for(int sid2_idx = 0; sid2_idx < sid2[sid1%100].length; sid2_idx++) {
			
			String final_page_num = final_page(sid1,/*sid2[sid1%100][sid2_idx]*/"252");
			
			for(int page_num = 1; page_num<Integer.parseInt(final_page_num); page_num++) { //모든 페이지 link 가져오기
				
				String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
						+ "sid2=" + /*sid2[sid1%100][sid2_idx]*/"252"
						+ "&sid1=" + sid1
//						+ "&date="
						+ "&page="+page_num;
				
				Document doc = Jsoup.connect(URL).get();
				
				Elements elements_test = doc.select("div.content div.list_body ul li dl"); //Aticle URL
				
				for(Element element : elements_test) {
					
					article_crawler.article_crawling(element.toString().split("href=\"")[1].split("\">")[0].replace("&amp;","&")); //해당 링크에서 crawling
				}
				
			}
		}
		
	}
	
	public String final_page(int sid1,String sid2) throws IOException { //마지막 페이지번호 가져오기
		
		int final_page_num = 0;
		String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
				+ "sid2=" + sid2
				+ "&sid1=" + sid1
//				+ "&date="
				+ "&page="+100000;
		
		Document doc = Jsoup.connect(URL).get();
		Elements elements_nextPage = doc.select("div.content div.paging strong");
				
		return elements_nextPage.toString().split("<strong>")[1].split("</strong>")[0];
	}
	
}
