package mine.nlp.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		
	public static Map<Integer, String> getInstancesFromCsv(String csvPath, String separator) {
		File file = new File(csvPath);
		Map<Integer, String> map = new HashMap<>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(separator);
				map.put(Integer.parseInt(fields[0]), fields[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static TreeMap<String, Integer> createDictionary(Map<Integer, String> docs, Boolean addFrequency) {
		TreeMap<String, Integer> dictionary = new TreeMap<>();
		List<String> tokens = new ArrayList<>();
		for (String docText : docs.values()) {
			tokens.addAll(Arrays.asList(docText.split("\\s+")));
		}

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

	public static Map<Integer, Map<String, Integer>> createDocVectors(Map<Integer, String> docs, Map<String, Integer> dictionary) {
		Map<Integer, Map<String, Integer>> docVectors = new LinkedHashMap<>();
		for (Integer docId : docs.keySet()) {
			String[] docTokens = docs.get(docId).split("\\s+");
			Map<String, Integer> docVector = new TreeMap<>();
			for (String dictToken : dictionary.keySet()) {
				Integer freq = Collections.frequency(Arrays.asList(docTokens), dictToken);
				docVector.put(dictToken, freq);
			}
			docVectors.put(docId, docVector);
		}
		return docVectors;
	}
	
	
	
	
	public static void main(String[] args) {
		
		//load documents from a csv into a map
		String filePath = "src/main/resources/sentences.csv";
		Map<Integer, String> docs = getInstancesFromCsv(filePath, "\\t");
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
