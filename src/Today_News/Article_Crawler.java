package Today_News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Article_Crawler {

	public void article_crawling(String URL) throws IOException {
		
		Document doc = Jsoup.connect(URL).get();

		Elements elements_article_text = doc.select("div.content div._article_body_contents");

		String delet_tag = "[<].*[>]";
		
		for(Element element : elements_article_text) {
			
			System.out.println(element.toString().replaceAll(delet_tag, "").replace("function _flash_removeCallback() {}", "").replace("// flash 오류를 우회하기 위한 함수 추가", ""));
		}
	}
	
}
