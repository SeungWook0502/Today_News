package Today_News;

import java.util.ArrayList;
import java.util.List;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class Title_Analysis {

	public ArrayList<String> Text_Analysis(String Content_Text) {
		
	    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); //Make Komoran Object of Wiki model
	
	    KomoranResult analyzeResultList = komoran.analyze(Content_Text); //Analysis Text -> return Keyword List
	
	    List<Token> tokenList = analyzeResultList.getTokenList(); //Store Keywords to List
	    ArrayList<String> keyWord = new ArrayList<String>();
	    
	    for (Token token : tokenList) { 
	        if(token.getPos().equals("NNP")) { //Expect NNP(고유명사) keyword
	        	if(token.getMorph().length()!=1) {
	        		keyWord.add(token.getMorph()); //Append Keyword
	        	}
	        }        
        }
	    return keyWord;
    }
}