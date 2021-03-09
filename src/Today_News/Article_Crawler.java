package Today_News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Article_Crawler {

	public ArrayList<String> article_crawling(String URL) throws IOException {
		
		Document doc = Jsoup.connect(URL).get();

		Elements elements_article_text = doc.select("div.content div._article_body_contents");
		Elements elements_article_title = doc.select("div.content div.article_header div.article_info h3");
		Elements elements_article_time = doc.select("div.content div.article_header div.article_info div.sponsor span.t11");
		
		
		ArrayList<String> Article_TTT = new ArrayList<String>();

		Article_TTT.add(elements_article_title.toString().split("<h3 id=\"articleTitle\">")[1].split("</h3>")[0]); //제목
		Article_TTT.add(elements_article_time.toString().split("<span class=\"t11\">")[1].split("</span")[0]); //기사입력 시간

		String delet_tag = "[<].*[>]";
		for(Element element : elements_article_text) { //내용
			
			Article_TTT.add(element.toString().replaceAll(delet_tag, "").replace("function _flash_removeCallback() {}", "").replace("// flash 오류를 우회하기 위한 함수 추가", ""));
		}
		
		return Article_TTT;
	}
	
}
