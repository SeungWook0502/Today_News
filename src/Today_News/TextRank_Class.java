package Today_News;

public class TextRank_Class {

	String Keyword_word; 	//키워드
	int Keyword_Count=0;	//키워드 누적
	

	public TextRank_Class(String Keyword) {
		this.Keyword_word = Keyword;
	}
	
	public String getKeyword() {
		return Keyword_word;
	}
	public void setKeyword(String keyword) {
		Keyword_word = keyword;
	}
	public int getKeyword_Rank() {
		return Keyword_Count;
	}
	public void setKeyword_Rank() {
		Keyword_Count = 0;
	}
	public void addKeyword_Rank() {
		Keyword_Count += 1;
	}

}
