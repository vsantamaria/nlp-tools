package mine.nlp.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
	
	public static LinkedHashMap<String, Object> sortMapByValues(Map<String, ?> map, boolean reverse) {
		List<String> mapKeys = new ArrayList<>(map.keySet());
		List  mapValues = new ArrayList<>(map.values());
		if (reverse) {
			Collections.sort(mapValues, Comparator.reverseOrder());			
		} else {
			Collections.sort(mapValues);
		}
		Collections.sort(mapKeys);
		LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<>();
		Iterator  valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				Object comp1 = map.get(key);
				Object comp2 = val;
				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}

	public static LinkedHashMap<String, Object> sortMapByValues(Map<String, ?> map) {
		return sortMapByValues(map, false);
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
	
	
	public static Double doubleTo2Decimals(double number) {
		BigDecimal bd = new BigDecimal(number).setScale(2, RoundingMode.FLOOR);
		return bd.doubleValue();
	}

}
