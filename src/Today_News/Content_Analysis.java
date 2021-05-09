package Today_News;

import java.util.List;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class Content_Analysis {

	public void Text_Analysis(String Content_Text) {
	    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
	
	    KomoranResult analyzeResultList = komoran.analyze(Content_Text);
	
	    List<Token> tokenList = analyzeResultList.getTokenList();
	    for (Token token : tokenList) {
	        if(token.getPos().equals("NNP")) {
	        	System.out.println(token.getMorph()+token.getPos());
	        
	        }        
        }
    }
}