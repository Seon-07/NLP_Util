package com.seon.util;

public class NlpUtilMain {
	
	
	
	public static void main(String[] args) throws Exception{
		NlpUtil nlp = new NlpUtil();
		System.out.println("NLP 실행");
		
		String str = "Mike works at Google. He lives in South Korea. He was born on 1996-07-22. He earned $800 last week.";
		
		//문장
//		for(String sen : nlp.sentence(str)) {
//			System.out.println(sen);
//		}
	
		//단어
//		for(String word : nlp.tokenizer(str)) {
//			System.out.println(word);
//		}
		
		//품사태깅
//		for(String word : nlp.posTagging(str)) {
//			System.out.println(word);
//		}
		
		//인식
		for(String word : nlp.recogName(str, "location")) {
			System.out.println(word);
		}
		
		
	}

}
