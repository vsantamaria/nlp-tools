package mine.nlp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * May 26, 2017
 * @author vsantamaria
 */
public class BagOfWordsTest {

	static List<String> documents;
	static Map<String, Integer> dictionary;

	@BeforeClass
	public static void setUp() {
		loadDocs();
		loadDictionary();
	}

	public static void loadDocs() {
		String csvPath = "src/main/resources/sentences.csv";
		documents = new ArrayList(Utils.getInstancesFromCsv(csvPath, "\\t").values());
	}

	public static void loadDictionary() {
		dictionary = BagOfWords.createDictionary(documents, true);
	}

	@Test
	//number of words in documents should be equal to the sum of word frequencies in dictionary
	public void createDictionaryTest() {
		int wordsInDocuments = 0;
		for (String document : documents) {
			wordsInDocuments += document.split("\\s+").length;
		}

		int sumOfFrequencies = 0;
		for (Integer frequency : dictionary.values()) {
			sumOfFrequencies += frequency;
		}
		assertEquals(wordsInDocuments, sumOfFrequencies);
	}

	@Test
	//dictionary should countain all the words in the documents
	public void createDictionaryTest2() {
		Set<String> wordsInDocuments = new HashSet<>();
		for (String document : documents) {
			wordsInDocuments.addAll(Arrays.asList(document.split("\\s+")));
		}
		//dictionary contains all the words in the documents
		assertTrue(dictionary.keySet().containsAll(wordsInDocuments));
		//number of distinct words in the documents is equal to number of words in dictionary
		assertEquals(dictionary.keySet().size(), wordsInDocuments.size());
	}

	@Test
	public void createDocVectorTest() {
		//number of words in document should be equal to the sum of word counts in doc vector
		for (String document : documents) {
			int wordsInDoc = document.split("\\s+").length;
			int wordCounts = 0;
			Map<String, Integer> docVector = BagOfWords.createDocVector(document, dictionary);
			for (Integer wordOcurrences : docVector.values()) {
				wordCounts += wordOcurrences;
			}
			System.out.println("\nDOCUMENT : " + document + " -- WORDS : " + wordsInDoc);
			System.out.println("DOC VECTOR : " + docVector + "\nSUM OF WORD COUNTS : " + wordCounts);
			assertEquals(wordsInDoc, wordCounts);
		}
	}

}
