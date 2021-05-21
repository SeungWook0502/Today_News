package Today_News;

import java.util.ArrayList;

public class Article_Class {

	String Article_Title; //제목
	String Article_Content; //내용
	String Article_Time; //개시 시간
	String Article_URL; //URL
	String Article_Sidnum; //Sid1 Number
	ArrayList<String> Article_Keyword = new ArrayList<String>(); //Keyword
	
	public String getArticle_Title() {
		return Article_Title;
	}
	public void setArticle_Title(String article_Title) {
		Article_Title = article_Title;
	}
	public String getArticle_Content() {
		return Article_Content;
	}
	public void setArticle_Content(String article_Content) {
		Article_Content = article_Content;
	}
	public String getArticle_Time() {
		return Article_Time;
	}
	public void setArticle_Time(String article_Time) {
		Article_Time = article_Time;
	}
	public String getArticle_URL() {
		return Article_URL;
	}
	public void setArticle_URL(String article_URL) {
		Article_URL = article_URL;
	}
	public String getArticle_Sidnum() {
		return Article_Sidnum;
	}
	public void setArticle_Sidnum(String article_Sidnum) {
		Article_Sidnum = article_Sidnum;
	}
	public ArrayList<String> getArticle_Keyword() {
		return Article_Keyword;
	}
	public void setArticle_Keyword(ArrayList<String> article_Keyword) {
		Article_Keyword = article_Keyword;
	}
	
}
