package Today_News;

import java.util.ArrayList;

public class Article_Class {

	String Article_Title; //����
	String Article_Content; //����
	String Article_Time; //���� �ð�
	String Article_URL; //URL
	String Article_sid2; //Sid2
	ArrayList<String> Article_Keyword = new ArrayList<String>(); //Keyword //Expect to Modify
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
	public String getArticle_sid2() {
		return Article_sid2;
	}
	public void setArticle_sid2(String article_sid2) {
		Article_sid2 = article_sid2;
	}
	public ArrayList<String> getArticle_Keyword() {
		return Article_Keyword;
	}
	public void setArticle_Keyword(ArrayList<String> article_Keyword) {
		Article_Keyword = article_Keyword;
	}
	
}
