package com.seon.util;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class NlpUtil {
	/**
	 * @param String
	 * @return String[]
	 * 2024. 6. 16.
	 * TODO 문장 분리
	 */
	public String[] sentence(String str) throws Exception{
		//모델
		InputStream sentenceModelStream = new FileInputStream("src/main/resources/en-sent.bin");
	    SentenceModel sentenceModel = new SentenceModel(sentenceModelStream);
	    SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
	    
	    //문장 분리
	    String[] sentences = sentenceDetector.sentDetect(str);
	    
	    // 리소스 해제
        sentenceModelStream.close();
        
	    return sentences;
	}
	
	
	/**
	 * @param String
	 * @return String[]
	 * 2024. 6. 16.
	 * TODO 토큰화
	 */
	public String[] tokenizer(String str) throws Exception{
		//모델
		InputStream tokenizerModelStream = new FileInputStream("src/main/resources/en-token.bin");
        TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelStream);
        TokenizerME tokenizer = new TokenizerME(tokenizerModel);
	    
        // 토큰화
        String[] tokens = tokenizer.tokenize(str);
	    
        // 리소스 해제
        tokenizerModelStream.close();
        
	    return tokens;
	}
	
}
