package Today_News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainApp {
	
	public static void main(String[] args) throws IOException {

		int[] sid1 = {100,101,102,103,104,105};
		
//		String URL = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&"
//				+ "sid2=" + 263
//				+ "&sid1=" + 101
////				+ "&date="
//				+ "&page="+1000000;
		
		String URL = "https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=103&oid=014&aid=0004597313";
		
		System.out.println("URL :: " + URL);
		
		URL_Crawler url_crawler = new URL_Crawler();
		
//		for(int i=0;i<sid1.length;i++) {
			System.out.println(/*sid1[i]*/102);
			url_crawler.select_sid2Num(/*sid1[i]*/102);
//		}
		
		//1. Document를 가져온다.
//		Document doc = Jsoup.connect(URL).get();
		
		//2. 목록을 가져온다.
////		System.out.println("" + doc.toString());
//		Elements elements_test = doc.select("div.content div.list_body ul li dl");
//		Elements elements_nextPage = doc.select("div.content div.paging strong");
//		Elements elements_article_text = doc.select("div.content div._article_body_contents");
//		Elements elements_article_title = doc.select("div.content div.article_header div.article_info h3");
//		Elements elements_article_time = doc.select("div.content div.article_header div.article_info div.sponsor span.t11");
//		
//		//3. 목록(배열)에서 정보를 가져온다.
////		String delet_tag = "[<].*[>]";
////		for(Element element : elements_article_text) {
////			System.out.println(element.toString().replaceAll(delet_tag, "").replace("function _flash_removeCallback() {}", "").replace("// flash 오류를 우회하기 위한 함수 추가", ""));
////		}
//
//		System.out.println(elements_article_title.toString().split("<h3 id=\"articleTitle\">")[1].split("</h3>")[0]);
//		
//		System.out.println(elements_article_time.toString().split("<span class=\"t11\">")[1].split("</span")[0]);
//		
//		
//		System.out.println(elements_nextPage.toString().split("<strong>")[1].split("</strong>")[0]);
		
	}
	
}