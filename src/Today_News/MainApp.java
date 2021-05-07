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
//		LocalTime startTime = LocalTime.now();
		
		URL_Crawler url_crawler = new URL_Crawler(); //URL Crawler
		
//		Mk_File(); //File initialization
		
		ArrayList<ArrayList<Article_Class>> Article = new ArrayList<ArrayList<Article_Class>>(); //News Data variable
		ArrayList<TextRank_Class> TextRank = new ArrayList<TextRank_Class>();
		
		for(int i=0;i<sid1.length;i++) { //Sending Sid1 values
			Article.add(url_crawler.select_sid2Num(sid1[i]/*102*/, TextRank)); //Send Sid1 value
		}
		
		MainApp mainApp = new MainApp();
		
		for(int sid1_Count = 0; sid1_Count < Article.size(); sid1_Count++) {
			for(int sid2_Count = 0; sid2_Count < Article.get(sid1_Count).size(); sid2_Count++) {
				
				mainApp.SendDB_Article(Article.get(sid1_Count).get(sid2_Count).getArticle_Title(),Article.get(sid1_Count).get(sid2_Count).getArticle_Content(),Article.get(sid1_Count).get(sid2_Count).getArticle_sid2(),Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				for(int Article_Keyword_Count = 0; Article_Keyword_Count < Article.get(sid1_Count).get(sid2_Count).Article_Keyword.size(); Article_Keyword_Count++) {
					
					mainApp.SendDB_Keyword_List(Article.get(sid1_Count).get(sid2_Count).Article_Keyword.get(Article_Keyword_Count), Article.get(sid1_Count).get(sid2_Count).getArticle_sid2(), Article.get(sid1_Count).get(sid2_Count).getArticle_URL());
				}
			}
		}
		for(int Keyword_Count = 0; Keyword_Count < TextRank.size(); Keyword_Count++) {
			mainApp.SendDB_Keyword_Rank(TextRank.get(Keyword_Count).Keyword_word,TextRank.get(Keyword_Count).Keyword_Count);
		}
		
		
//		for(int a=0;a<Article.size();a++) { //Check article list
//			for(int b=0;b<Article.get(a).size();b++) {
//				
//				System.out.println(Article.get(a).get(b).getTitle()+"\t"+Article.get(a).get(b).getSid2());
//			}
//		}
//		for(int a=0;a<TextRank.size();a++) { //Check Keyword list
//			if(TextRank.get(a).getKeyword_Rank()!=0) {
//				System.out.println(TextRank.get(a).getKeyword()+" "+" "+TextRank.get(a).getSid2());
//			}
//		}
//		LocalTime endTime = LocalTime.now();
//		System.out.println(String.valueOf(endTime.getMinute()-startTime.getMinute())+"분"+String.valueOf(endTime.getSecond()-startTime.getSecond())+"초");
//		
	}
	
	public void SendDB_Article(String Article_Title, String Article_Content, String Article_Sidnum, String Article_URL) { //Insert_Article
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Article.php" + "?" + URLEncoder.encode("Article_Title") + "=" + URLEncoder.encode(Article_Title,"UTF-8")+"&"+URLEncoder.encode("Article_Content") + "=" + URLEncoder.encode(Article_Content,"UTF-8")+"&"+URLEncoder.encode("Article_Sidnum") + "=" + URLEncoder.encode(Article_Sidnum,"UTF-8")+"&"+URLEncoder.encode("Article_URL") + "=" + URLEncoder.encode(Article_URL,"UTF-8"));
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        char[] buff = new char[512];
	        int len = -1;
	        while( (len = br.read(buff)) != -1) {
	           System.out.print(new String(buff, 0, len));
	        }
	        br.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SendDB_Keyword_List(String Keyword_Word, String Keyword_Sidnum, String Keyword_URL) { //Insert_Keyword_List
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Keyword_List.php" + "?" + URLEncoder.encode("Keyword_Word") + "=" + URLEncoder.encode(Keyword_Word,"UTF-8")+"&"+URLEncoder.encode("Keyword_Sidnum") + "=" + URLEncoder.encode(Keyword_Sidnum,"UTF-8")+"&"+URLEncoder.encode("Keyword_URL") + "=" + URLEncoder.encode(Keyword_URL,"UTF-8"));
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        char[] buff = new char[512];
	        int len = -1;
	        while( (len = br.read(buff)) != -1) {
	           System.out.print(new String(buff, 0, len));
	        }
	        br.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void SendDB_Keyword_Rank(String Keyword_Word, int Keyword_Count) { //Insert_Keyword_Rank
		try {
			URL url = new URL("http://todaynews.dothome.co.kr/Insert_Keyword_Rank.php" + "?" + URLEncoder.encode("Keyword_Word") + "=" + URLEncoder.encode(Keyword_Word,"UTF-8")+"&"+URLEncoder.encode("Keyword_Count") + "=" + Keyword_Count);
			URLConnection connect = url.openConnection(); //url연결
			connect.setUseCaches(false);
			InputStream is = connect.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        char[] buff = new char[512];
	        int len = -1;
	        while( (len = br.read(buff)) != -1) {
	           System.out.print(new String(buff, 0, len));
	        }
	        br.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void Mk_File() throws IOException{ //File initialization //No use
//
//		File Article_Data_File = new File("Article_Data.txt");
//		
//		BufferedWriter filewriter = new BufferedWriter(new FileWriter(Article_Data_File));
//		
//		filewriter.close();
//	}
	
}