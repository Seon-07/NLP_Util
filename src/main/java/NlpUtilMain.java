import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class NlpUtilMain {

	public static void main(String[] args) {
		System.out.println("NLP 실행");
		try {
            // 문장 분리 모델 로드
            InputStream sentenceModelStream = new FileInputStream("src/main/resources/en-sent.bin");
            SentenceModel sentenceModel = new SentenceModel(sentenceModelStream);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            
            // 토크나이저 모델 로드
            InputStream tokenizerModelStream = new FileInputStream("src/main/resources/en-token.bin");
            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelStream);
            TokenizerME tokenizer = new TokenizerME(tokenizerModel);
            
            // 예제 텍스트
            String text = "Life is a journey, not a destination.Success is not final, failure is not fatal It is the courage to continue that counts.";
            
            // 문장 분리
            String[] sentences = sentenceDetector.sentDetect(text);
            
            // 각 문장에 대해 토큰화
            for (String sentence : sentences) {
                System.out.println("Sentence: " + sentence);
                
                // 토큰화
                String[] tokens = tokenizer.tokenize(sentence);
                
                // 토큰 출력
                for (String token : tokens) {
                    System.out.println("Token: " + token);
                }
            }
            
            // 리소스 해제
            sentenceModelStream.close();
            tokenizerModelStream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
