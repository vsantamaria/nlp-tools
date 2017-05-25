/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mine.nlp.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	return (double) wordOcurrences / numberOfWords;
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
	return (double) Math.log(docs.size() / docsWithWord);
    }
    
    // TFIDF = TF * IDF
    public static Double getTfIdf (String word, List<String> words, List<String> docs) {
	Double tf = getTf(word, words);
	Double idf = getIdf(word, docs);
	return tf * idf;    
    }
    
    public static String tfIdfToString(String word, Double tf, Double idf, Double tfIdf) {
	DecimalFormat df = new DecimalFormat("0.00");
	return String.format("string= %1$-15s tf= %2$-15s idf= %3$-15s tfIdf= %4$-15s", 
		word, df.format(tf), df.format(idf), df.format(tfIdf));
    }
    
    
    public static void main(String[] args) {	
	
	//load documents from a csv to a list of strings
	String csvPath = "src/main/resources/sentences.csv";
	Map<Integer, String> docsMap = Utils.getInstancesFromCsv(csvPath, "\\t");
	List<String> docs = new ArrayList<>(docsMap.values());
	
	//for each document, calculate tf, idf, tfIdf
	for (String doc : docs) {
	    String  docPreprocessed = TextHelper.preprocessText(doc);
	    System.out.println("\nDocument: " + doc);
	    List<String> words = Arrays.asList(docPreprocessed.split("\\s+"));
	    for (String word : words) {
		Double tf = getTf(word, words);
		Double idf= getIdf(word, docs);
		Double tfidf = tf * idf;
		System.out.println(tfIdfToString(word, tf, idf, tfidf));
	    }
	}
	
    }

}
