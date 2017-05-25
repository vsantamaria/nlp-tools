package mine.nlp.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date May 20, 2017
 * @author vsantamaria
 */

public class Utils {

    public static void writeStringToFile(String text, String path) {
	try {
	    Files.write(Paths.get(path), text.getBytes(),
		    StandardOpenOption.CREATE);
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

    public static Map<Integer, String> getInstancesFromCsv(String csvPath,
	    String separator) {
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
    

}
