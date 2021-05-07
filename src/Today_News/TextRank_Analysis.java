package Today_News;

import java.util.ArrayList;

public class TextRank_Analysis {

	public void TextRanking(ArrayList<TextRank_Class> Keyword_List,String keyword) {
		
		boolean newKeyword=false;
		
		for(int i = 0; i < Keyword_List.size(); i++) { //compare keyword list to keyword
				
			if(Keyword_List.get(i).getKeyword().equals(keyword)) {
				
				Keyword_List.get(i).addKeyword_Rank();
				newKeyword = true;
				break;
			}
		}
		if(newKeyword) {
			TextRank_Class textrank_class = new TextRank_Class(keyword);
			Keyword_List.add(textrank_class);
			System.out.println(textrank_class.Keyword_word);
		}
		
//		for(int i = 0; i< textrank_class_list.size();i++) {
//			
//			TextRank_Class textrank_class = new TextRank_Class(keyword);
//			textrank_class_list.add(textrank_class);
//			textrank_class.setSid2(sid2);
//		
//		}
	}
}
