package mine.nlp.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import static mine.nlp.tools.Utils.mapPretty;
import static mine.nlp.tools.Utils.writeStringToFile;

/**
 * @date May 20, 2017
 * @author vsantamaria
 */


public class BagOfWords {
		

	public static TreeMap<String, Integer> createDictionary(List<String> docs, Boolean addFrequency) {
		List<String> tokens = new ArrayList<>();
		for (String docText : docs) {
			tokens.addAll(Arrays.asList(docText.split("\\s+")));
		}
		
		TreeMap<String, Integer> dictionary = new TreeMap<>();
		Set<String> uniqueTokens = new TreeSet<>(tokens);
		for (String token : uniqueTokens) {
			Integer frequency = null;
			if (addFrequency) {
				frequency = Collections.frequency(tokens, token);
			}
			dictionary.put(token, frequency);
		}
		return dictionary;
	}
	
	public static TreeMap<String, Integer> createDictionary(Map<Integer, String> docs, Boolean addFrequency) {
		return createDictionary(new ArrayList<>(docs.values()), addFrequency);
	}

	
	public static Map<Integer, Map<String, Integer>> createDocVectors(Map<Integer, String> docs, Map<String, Integer> dictionary) {
		Map<Integer, Map<String, Integer>> docVectors = new LinkedHashMap<>();
		for (Integer docId : docs.keySet()) {
			String doc = docs.get(docId);
			docVectors.put(docId, createDocVector(doc, dictionary));
		}
		return docVectors;
	}
	
	public static List<Map<String, Integer>> createDocVectors(List<String> docs, Map<String, Integer> dictionary) {
		List<Map<String, Integer>> docVectors = new ArrayList<>();
		for (String doc : docs) {
		    docVectors.add(createDocVector(doc, dictionary));
		}
		return docVectors;
	}	
	
	
	public static Map<String, Integer> createDocVector(String doc, Map<String, Integer> dictionary) {
	    String[] docTokens = doc.split("\\s+");
	    Map<String, Integer> docVector = new HashMap<>();
	    for (String dictToken : dictionary.keySet()) {
		    Integer freq = Collections.frequency(Arrays.asList(docTokens), dictToken);
		    docVector.put(dictToken, freq);
	    }
	    return docVector;
	}
	
	
	
	public static void main(String[] args) {
		
		//load documents from a csv into a map
		String filePath = "src/main/resources/sentences.csv";
		Map<Integer, String> docs = Utils.getInstancesFromCsv(filePath, "\\t");
		System.out.println("DOCUMENTS\n(column1=docId : column2=text)\n"+mapPretty(docs));
		
		//create a dictionary containing the distinct words in the documents (and optionally their frequency)
		Map<String, Integer> dictionary = createDictionary(docs, true);
		String dictPath = "src/main/resources/bow.dic";
		writeStringToFile(mapPretty(dictionary), dictPath);
		System.out.println("\nDICCIONARY\n(column1=entry : column2=frequency)\n" + mapPretty(dictionary));
		
		//return document vectors. Each vector contains the number of times each word from the dictionary appears in the document
		Map<Integer, Map<String, Integer>> docVectors = createDocVectors(docs, dictionary);
		System.out.println("\nFEATURE VECTORS\n(column1=docId : column2=feature vector)\n"+mapPretty(docVectors));
	}

}
