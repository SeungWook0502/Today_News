package Today_News;

public class TextRank_Class {

	String Keyword; 	//키워드
	int Keyword_Rank=0;	//키워드 랭크
	String sid2;		//sid2
	
	public TextRank_Class(String Keyword) {
		this.Keyword = Keyword;
	}
	
	public String getKeyword() {
		return Keyword;
	}
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	public int getKeyword_Rank() {
		return Keyword_Rank;
	}
	public void setKeyword_Rank() {
		Keyword_Rank = 0;
	}
	public void addKeyword_Rank() {
		Keyword_Rank += 1;
	}
	public String getSid2() {
		return sid2;
	}
	public void setSid2(String sid2) {
		this.sid2 = sid2;
	}

}
