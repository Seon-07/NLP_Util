package com.seon.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

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
	
	/**
	 * @param String
	 * @return String[]
	 * 2024. 6. 22.
	 * TODO 품사 태깅
	 */
	public String[] posTagging(String str) throws Exception {
		String[] tokens = tokenizer(str);
	    
	    // 품사 태깅 모델
	    InputStream posModelStream = new FileInputStream("src/main/resources/en-pos-maxent.bin");
	    POSModel posModel = new POSModel(posModelStream);
	    POSTaggerME tagger = new POSTaggerME(posModel);

	    // 품사 태깅
	    String[] tags = tagger.tag(tokens);

	    // 리소스 해제
	    posModelStream.close();
	    
	    // 품사 태그를 한국어로 번역하기 위한 맵
	    Map<String, String> tagsTrans = new HashMap<>();
	    tagsTrans.put("NN", "명사");
	    tagsTrans.put("VBZ", "동사");
	    tagsTrans.put("DT", "한정사");
	    tagsTrans.put("RB", "부사");
	    tagsTrans.put("JJ", "형용사");
	    tagsTrans.put(",", "쉼표");
	    tagsTrans.put("PRP", "대명사");
	    tagsTrans.put("TO", "to부정사");
	    tagsTrans.put("VB", "동사");
	    tagsTrans.put("IN", "전치사 또는 접속사");
	    tagsTrans.put("NNS", "복수명사");
	    tagsTrans.put("NNP", "고유명사");
	    tagsTrans.put(".", "마침표");

	    // 결과 배열 생성 (토큰과 태그 결합)
	    String[] result = new String[tokens.length];
	    for (int i = 0; i < tokens.length; i++) {
	        String translatedTag = tagsTrans.getOrDefault(tags[i], tags[i]); // 번역된 태그
	        result[i] = tokens[i] + " - " + translatedTag;
	    }
	    return result;
	}
	
	/**
	 * @param String
	 * @return String[]
	 * 2024. 6. 22.
	 * TODO 텍스트에서 이름 엔터티를 인식하고 한글로 매핑하여 반환하는 메서드
	 */
	public String[] recogName(String str, String division) throws Exception {
		String[] tokens = tokenizer(str);
		InputStream stream = null;
		Span[] resultSpan = null;
		// 엔터티 인식을 위한 모델들 로드
		switch (division) {
			case "person":
				stream = new FileInputStream("src/main/resources/en-ner-person.bin");
				TokenNameFinderModel personModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(personModel).find(tokens);
				System.out.println("==========log: person-model create");
				break;
			case "organization":
				stream = new FileInputStream("src/main/resources/en-ner-organization.bin");
				TokenNameFinderModel organizationModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(organizationModel).find(tokens);
				System.out.println("==========log: organization-model create");
				break;
			case "location":
				stream = new FileInputStream("src/main/resources/en-ner-location.bin");
				TokenNameFinderModel locationModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(locationModel).find(tokens);
				System.out.println("==========log: location-model create");
				break;
			case "date":
				stream = new FileInputStream("src/main/resources/en-ner-date.bin");
				TokenNameFinderModel dateModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(dateModel).find(tokens);
				System.out.println("==========log: date-model create");
				break;
			case "time":
				stream = new FileInputStream("src/main/resources/en-ner-time.bin");
				TokenNameFinderModel timeModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(timeModel).find(tokens);
				System.out.println("==========log: time-model create");
				break;
			case "money":
				stream = new FileInputStream("src/main/resources/en-ner-money.bin");
				TokenNameFinderModel moneyModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(moneyModel).find(tokens);
				System.out.println("==========log: money-model create");
				break;
			case "percentage":
				stream = new FileInputStream("src/main/resources/en-ner-percentage.bin");
				TokenNameFinderModel percentageModel = new TokenNameFinderModel(stream);
				resultSpan = new NameFinderME(percentageModel).find(tokens);
				System.out.println("==========log: percentage-model create");
				break;
		}
	    
		String[] result = new String[resultSpan.length];
        for (int i = 0; i < resultSpan.length; i++) {
            Span span = resultSpan[i];
            String spanText = String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd()));
            result[i] = spanText;
        }
	    System.out.println("==========log: recogName end");
	    // 결과 반환
	    stream.close();
	    return result;
	}
}
