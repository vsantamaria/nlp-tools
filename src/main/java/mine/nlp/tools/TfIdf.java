package mine.nlp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mine.nlp.tools.helpers.TextHelper;

/**
 * May 25, 2017
 * @author vsantamaria
 */
public class TfIdf {

	//TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document).
	public static Double getTf(String word, List<String> words) {
		Integer numberOfWords = words.size();
		Integer wordOcurrences = Collections.frequency(words, word);
		Double tf = (double) wordOcurrences / numberOfWords;
		return Utils.doubleTo2Decimals(tf);
	}

	//IDF(t) = log_e(Total number of documents / Number of documents with term t in it).
	public static Double getIdf(String word, List<String> docs) {
		Integer docsWithWord = 0;
		for (String docText : docs) {
			docText = TextHelper.preprocessText(docText);
			List<String> doc = Arrays.asList(docText.split("\\s+"));
			Integer wordOcurrences = Collections.frequency(doc, word);
			if (wordOcurrences > 0) {
				docsWithWord++;
			}
		}
		Double result;
		if (docsWithWord == 0) {
			result = 0.0;
		} else {
			result = Math.log((double) docs.size() / docsWithWord);
		}
		return Utils.doubleTo2Decimals(result);
	}

	// TFIDF = TF * IDF
	public static Double getTfIdf(String word, List<String> words, List<String> docs) {
		Double tf = getTf(word, words);
		Double idf = getIdf(word, docs);
		return Utils.doubleTo2Decimals(tf * idf);
	}

	public static String tfIdfToString(String word, Double tf, Double idf, Double tfIdf) {
		return String.format("string= %1$-15s tf= %2$-15s idf= %3$-15s tfIdf= %4$-15s",
			word, tf, idf, tfIdf);
	}

	public static void main(String[] args) {

		//load documents from a csv to a list of strings
		String csvPath = "src/main/resources/sentences.csv";
		Map<Integer, String> docsMap = Utils.getInstancesFromCsv(csvPath, "\\t");
		List<String> docs = new ArrayList<>(docsMap.values());

		//for each word in each document, calculate tf, idf, tfIdf
		for (String doc : docs) {
			String docPreprocessed = TextHelper.preprocessText(doc);
			System.out.println("\nDocument: " + doc);
			List<String> words = Arrays.asList(docPreprocessed.split("\\s+"));
			Map<String, Double> result = new HashMap<>();
			for (String word : words) {
				Double tf = getTf(word, words);
				Double idf = getIdf(word, docs);
				Double tfidf = Utils.doubleTo2Decimals(tf * idf);
				System.out.println(tfIdfToString(word, tf, idf, tfidf));
				result.put(word, tfidf);
			}
			System.out.println("WORDS SORTED BY TFIDF : " + Utils.sortMapByValues(result, true));
		}
	}
}
