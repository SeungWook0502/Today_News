package Today_News;

import java.util.ArrayList;

public class Article_Class {

	String Title;
	String Content;
	String Time;
	String URL;
	String sid2;
	ArrayList<String> Keyword = new ArrayList<String>();
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getSid2() {
		return sid2;
	}
	public void setSid2(String sid2) {
		this.sid2 = sid2;
	}
	public ArrayList<String> getKeyword() {
		return Keyword;
	}
	public void setKeyword(ArrayList<String> Keyword) {
		this.Keyword = Keyword;
	}
}
