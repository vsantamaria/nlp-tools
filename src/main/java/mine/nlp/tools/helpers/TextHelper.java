package mine.nlp.tools.helpers;

/**
 * May 25, 2017
 * @author vsantamaria
 */

public class TextHelper {

    public static String preprocessText(String text) {
	String result = text.toLowerCase();
	result = removeAccentMarks(result);
	result = removeNonAlfa(result);
	return result;
    }

    public static String removeAccentMarks(String text) {
	text = text.replace("á", "a");
	text = text.replace("é", "e");
	text = text.replace("í", "i");
	text = text.replace("ó", "o");
	text = text.replace("ú", "u");
	return text;
    }

    public static String removeNonAlfa(String text) {
	return text.replaceAll("[^A-Za-zÀ-\u00ff\\s]", "");
    }

}
