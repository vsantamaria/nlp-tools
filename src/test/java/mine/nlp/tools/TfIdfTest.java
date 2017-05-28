package mine.nlp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * May 26, 2017
 * @author vsantamaria
 */

public class TfIdfTest {
	
	static List<String> documents;
	
	@BeforeClass
	public static void setUp(){
		String csvPath = "src/main/resources/sentences.csv";
		Map<Integer, String> docs = Utils.getInstancesFromCsv(csvPath, "\\t");
		documents = new ArrayList<>(docs.values());
	}
	
	@Test
	public void getTfTest(){
		String emptyWord = "";
		for (String document : documents) {
			List<String> words = Arrays.asList(document.split("\\s+"));
			//tf for empty string should be 0
			assertTrue(TfIdf.getTf(emptyWord, words) == 0);
			//if word is in words, tf should be greater than or equal to 1 / words.size() 
			String firstWordInDoc = words.get(0);
			assertTrue(TfIdf.getIdf(firstWordInDoc, words) >= 1 / words.size());
		}	
	}
	
	@Test
	public void getIdfTest(){
		String wordEmpty = "";
		String anotherWord = documents.get(0).split("\\s+")[0]; //first word of first document
		//empty string should return idf = 0
		assertTrue(TfIdf.getIdf(wordEmpty, documents) == 0);
		//a word that exists in the documents sould return idf > 0
		assertTrue(TfIdf.getIdf(anotherWord, documents) > 0.0);
	}
	
}
