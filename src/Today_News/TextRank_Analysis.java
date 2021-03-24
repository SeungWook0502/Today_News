package Today_News;

import java.util.ArrayList;

public class TextRank_Analysis {

	public void TextRanking(ArrayList<TextRank_Class> textrank_class_list,String keyword,String sid2) {
		
		int addKeyword_cnt=0;
		
		for(int i = 0; i < textrank_class_list.size(); i++) {
				
			if(textrank_class_list.get(i).getKeyword().equals(keyword)) {
				
				textrank_class_list.get(i).addKeyword_Rank();
				addKeyword_cnt++;
			}
		}
		if(addKeyword_cnt==0) {
			TextRank_Class textrank_class = new TextRank_Class(keyword);
			textrank_class_list.add(textrank_class);
			textrank_class.setSid2(sid2);
		}
	}
}
