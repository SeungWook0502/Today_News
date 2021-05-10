package Today_News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainApp {
	
	public static void main(String[] args) throws Exception {

		int[] sid1 = {100,101,102,103,104,105}; //Sid1 value
		MainApp mainApp = new MainApp();
		
		URL_Crawler url_crawler = new URL_Crawler(); //URL Crawler
		
		ArrayList<ArrayList<Article_Class>> Article = new ArrayList<ArrayList<Article_Class>>(); //News Data variable
		ArrayList<TextRank_Class> Keyword_List = new ArrayList<TextRank_Class>();
		
		//Reset DB//
		mainApp.Delete_DBData("Article");
		mainApp.Delete_DBData("Keyword_List");
		mainApp.Delete_DBData("Keyword_Rank");
		
		//Crawling//
		for(int i=0;i<sid1.length;i++) { //Sending Sid1 values
			Article.add(url_crawler.select_sid2Num(sid1[i]/*102*/, Keyword_List)); //Send Sid1 value
		}
		
		//Store DB//
		for(int sid1_Count = 0; sid1_Count < Article.size(); sid1_Count++) {
			for(int sid2_Count = 0; sid2_Count < Article.get(sid1_Count).size(); sid2_Count++) {
				
				mainApp.Insert_Article(Article.get(sid1_Count).get(sid2_Count).getArticle_Title(),Article.get(sid1_Count).get(sid2_Count).getArticle_Content(),Article.get(sid1_Count).get(sid2_Count).getArticle_sid2(),Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				for(int Article_Keyword_Count = 0; Article_Keyword_Count < Article.get(sid1_Count).get(sid2_Count).Article_Keyword.size(); Article_Keyword_Count++) {
					
					mainApp.Insert_Keyword_List(Article.get(sid1_Count).get(sid2_Count).Article_Keyword.get(Article_Keyword_Count), Article.get(sid1_Count).get(sid2_Count).getArticle_sid2(), Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				}
			}
		}
		for(int Keyword_Count = 0; Keyword_Count < Keyword_List.size(); Keyword_Count++) {
			mainApp.Insert_Keyword_Rank(Keyword_List.get(Keyword_Count).Keyword_word,Keyword_List.get(Keyword_Count).Keyword_Count);
		}
	}
	
	//Method
	public void Insert_Article(String Article_Title, String Article_Content, String Article_Sidnum, String Article_URL) { //Insert_Article
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Article.php" + "?" + URLEncoder.encode("Article_Title") + "=" + URLEncoder.encode(Article_Title,"UTF-8")+"&"+URLEncoder.encode("Article_Content") + "=" + URLEncoder.encode(Article_Content,"UTF-8")+"&"+URLEncoder.encode("Article_Sidnum") + "=" + URLEncoder.encode(Article_Sidnum,"UTF-8")+"&"+URLEncoder.encode("Article_URL") + "=" + URLEncoder.encode(Article_URL,"UTF-8"));
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        br.close();
	        
		} catch (Exception e) {
			System.out.println("Insert Error - Article");
		}
	}
	
	public void Insert_Keyword_List(String Keyword_Word, String Keyword_Sidnum, String Keyword_URL) { //Insert_Keyword_List
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Keyword_List.php" + "?" + URLEncoder.encode("Keyword_Word") + "=" + URLEncoder.encode(Keyword_Word,"UTF-8")+"&"+URLEncoder.encode("Keyword_Sidnum") + "=" + URLEncoder.encode(Keyword_Sidnum,"UTF-8")+"&"+URLEncoder.encode("Keyword_URL") + "=" + URLEncoder.encode(Keyword_URL,"UTF-8"));
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        br.close();
	        
		} catch (Exception e) {
			System.out.println("Insert Error - Keyword_List");
		}
	}
	

	public void Insert_Keyword_Rank(String Keyword_Word, int Keyword_Count) { //Insert_Keyword_Rank
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Keyword_Rank.php" + "?" + URLEncoder.encode("Keyword_Word") + "=" + URLEncoder.encode(Keyword_Word,"UTF-8")+"&"+URLEncoder.encode("Keyword_Count") + "=" + Keyword_Count);
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        br.close();
	        
		} catch (Exception e) {
			System.out.println("Insert Error - Keyword_Rank");
		}
	}
	
	public void Delete_DBData(String Table_Name) { //Delete_Data
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Delete_DBData.php" + "?" + URLEncoder.encode("Table") + "=" + URLEncoder.encode(Table_Name));
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        br.close();
	        
		}catch(Exception e) {
			System.out.println("Delete Error - Article");
		}
		
	}
}