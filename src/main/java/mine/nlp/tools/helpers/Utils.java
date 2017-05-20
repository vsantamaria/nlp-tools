package mine.nlp.tools.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date May 20, 2017
 * @author vsantamaria
 */

public class Utils {

	
	public static void writeStringToFile(String text, String path) {
		try {
			Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String mapPretty(Map<?, ?> map) {
		String result = map.entrySet()
				.stream()
				.map(entry -> entry.getKey() + " : " + entry.getValue())
				.collect(Collectors.joining("\n"));
		return result;
	}

}
