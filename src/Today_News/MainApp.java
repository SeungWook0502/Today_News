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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainApp {
	
	public static void main(String[] args) throws Exception {

		int[] sid1 = {100,101,102,103,104,105}; //Sid1 value
		MainApp mainApp = new MainApp();
		
		URL_Crawler url_crawler = new URL_Crawler(); //URL Crawler
		
		ArrayList<ArrayList<Article_Class>> Article = new ArrayList<ArrayList<Article_Class>>(); //News Data variable
		ArrayList<TextRank_Class> Keyword_List = new ArrayList<TextRank_Class>();
		
		//Crawling//
		for(int i=0;i<sid1.length;i++) { //Sending Sid1 values
			Article.add(url_crawler.select_sid2Num(sid1[i]/*102*/, Keyword_List)); //Send Sid1 value
		}
		
		//Reset DB//
		mainApp.Delete_DBData("Article"); //Delete_Data
		mainApp.Delete_DBData("Keyword_List"); //Delete_Data
		mainApp.Delete_DBData("Keyword_Rank"); //Delete_Data
		
		//Store DB//
		for(int sid1_Count = 0; sid1_Count < Article.size(); sid1_Count++) {
			for(int sid2_Count = 0; sid2_Count < Article.get(sid1_Count).size(); sid2_Count++) { //Insert_Article
				mainApp.Insert_Article(Article.get(sid1_Count).get(sid2_Count).getArticle_Title(),Article.get(sid1_Count).get(sid2_Count).getArticle_Content(),Article.get(sid1_Count).get(sid2_Count).getArticle_Sidnum(),Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				for(int Article_Keyword_Count = 0; Article_Keyword_Count < Article.get(sid1_Count).get(sid2_Count).Article_Keyword.size(); Article_Keyword_Count++) { //Insert_Keyword_List
					mainApp.Insert_Keyword_List(Article.get(sid1_Count).get(sid2_Count).Article_Keyword.get(Article_Keyword_Count), Article.get(sid1_Count).get(sid2_Count).getArticle_Sidnum(), Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				}
			}
		}
		for(int Keyword_Count = 0; Keyword_Count < Keyword_List.size(); Keyword_Count++) { //Insert_Keyword_Rank
			mainApp.Insert_Keyword_Rank(Keyword_List.get(Keyword_Count).Keyword_word,Keyword_List.get(Keyword_Count).Keyword_Count);
		}
		mainApp.Insert_Data_State();
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
			System.out.println(Article_Title+Article_Content+Article_Sidnum+Article_URL);
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
			System.out.println(Keyword_Word+Keyword_Sidnum+Keyword_URL);
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
			System.out.println(Keyword_Word+Keyword_Count);
		}
	}
	
	public void Insert_Data_State() { //Insert_Data_State
		DateTimeFormatter SQL_DateTime_Form = DateTimeFormatter.ofPattern("YYYY-MM-dd"+"%20"+"HH:MM:ss"); //MySQL DateTime pattern
		LocalDateTime Data_Upload_DateTime = LocalDateTime.now();
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Data_State.php" + "?"+ URLEncoder.encode("Data_Upload_DateTime") + "=" + Data_Upload_DateTime.format(SQL_DateTime_Form).toString() + "&" + URLEncoder.encode("State_Code") + "=" + 0 );
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        br.close();
	        
		}catch(Exception e) {
			System.out.println("Insert Error - State_code");
			System.out.println(Data_Upload_DateTime.format(SQL_DateTime_Form).toString());
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
			System.out.println(Table_Name);
		}
	}
}