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


		String URL = "https://news.naver.com/";
		System.out.println("URL :: " + URL);
		
		//1. Document를 가져온다.
		Document doc = Jsoup.connect(URL).get();
		
		//2. 목록을 가져온다.
//		System.out.println("" + doc.toString());
		Elements elements_head = doc.select("div.main_content div.hdline_article_tit");
		Elements elements_etc = doc.select("div.main_content div.mtype_list_wide li");
		
		//3. 목록(배열)에서 정보를 가져온다.
		int idx = 0;
		for(Element element : elements_head) {


			if(!element.toString().contains("동영상기사")){
			
				System.out.println(++idx + "head_title : " + element.toString().split("> ")[2].split(" <")[0]);
				System.out.println(idx + "head_hylnk : " + element.toString().split("href=\"")[1].split("\"")[0].replace("&amp;", "&").replace("/main/", "https://news.naver.com/main/"));
				System.out.println("==========================================");
			}
			
		}
		
		idx = 0;
		for(Element element : elements_etc) {
			
			if(!element.toString().contains("동영상기사")){
				
				System.out.println(++idx + "etc_title : " + element.toString().split("<strong>")[1].split("</strong")[0]);
				System.out.println(++idx + "etc_hylnk : " + element.toString().split("href=")[1].split(" class=")[0].split("\"")[1].replace("&amp;", "&"));
				System.out.println("==========================================");
			}
			
		}
	}
	
	public void Article(String article_hyplnk) throws IOException{
		
		String URL = article_hyplnk;
		
		System.out.println("URL :: " + URL);
		
		//1. Document를 가져온다.
		Document doc = Jsoup.connect(URL).get();

		//2. 목록을 가져온다.
		Elements elements_head = doc.select("div.main_content div.hdline_article_tit");
	}
	
}