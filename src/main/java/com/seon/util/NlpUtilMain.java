package com.seon.util;

public class NlpUtilMain {
	
	
	
	public static void main(String[] args) throws Exception{
		NlpUtil nlp = new NlpUtil();
		System.out.println("NLP 실행");
		
		String str = "Life is a journey, not a destination.Success is not final, failure is not fatal It is the courage to continue that counts.";
		
		//syso
		for(String sen : nlp.sentence(str)) {
			System.out.println(sen+"/");
		}
		
		for(String word : nlp.tokenizer(str)) {
			System.out.println(word+"/");
		}
	}

}
