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

	public ArrayList<String> article_crawling(String URL) throws Exception{
		
		
		Document doc = Jsoup.connect(URL).get(); // get Article page document

		Elements elements_article_text = doc.select("div.content div._article_body_contents"); //Article Content
		Elements elements_article_title = doc.select("div.content div.article_header div.article_info h3"); //Article Title
		Elements elements_article_time = doc.select("div.content div.article_header div.article_info div.sponsor span.t11"); //Article upload time
		
		ArrayList<String> Article_TTT = new ArrayList<String>();
		
		 //Article URL//
		try {
			Article_TTT.add(URL);
		}catch (ArrayIndexOutOfBoundsException exception) {
			Article_TTT.add("Non");
		}
		
		 //Article Title//
		try {
		Article_TTT.add(elements_article_title.toString().split("<h3 id=\"articleTitle\">")[1].split("</h3>")[0]); //Article Title
		}catch (ArrayIndexOutOfBoundsException exception){ //...같은 특수기호로 인한 예외처리
			Article_TTT.add("Non");
		}
		
		//Article Upload Time
		try {
			Article_TTT.add(elements_article_time.toString().split("<span class=\"t11\">")[1].split("</span")[0]); //Article Upload Time
		}catch(ArrayIndexOutOfBoundsException exception) {
			Article_TTT.add("Non");
		}

		//Article Content + Summarizer
		String[] delet_txt = {"&#x[0-9]{4};","&[a-z]{4}|&[a-z]{3}|&[a-z]{2}","[<].*[>]"}; //document text 제거용 정규식
		try {
		for(Element element : elements_article_text) { //내용
			Article_Summarizer article_summarizer = new Article_Summarizer();
				Article_TTT.add(article_summarizer.summarize(element.toString().replaceAll(delet_txt[2], "").replace("function _flash_removeCallback() {}", "").replace("// flash 오류를 우회하기 위한 함수 추가", "").replaceAll("\n","").replaceAll("|", "").replace("&nbsp;","").replaceAll(delet_txt[1], "").replaceAll(delet_txt[0],""))); //Article Content (1.remove tag 2.summarize)
		}
		}catch(ArrayIndexOutOfBoundsException exception) {
			Article_TTT.add("Non");
		}

		
		return Article_TTT;
		
	}
	
}
