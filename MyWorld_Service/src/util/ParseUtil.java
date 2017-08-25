package util;

import java.util.HashMap;

public class ParseUtil {
	public static HashMap<String, String> strToMap(String str) {

		str = str.substring(1, str.length() - 2);
		HashMap<String, String> resultMap = new HashMap<>();
		String[] items = str.split(",");
		String[] itemStrs = new String[2];
		for (String item : items) {
			itemStrs = item.split(":");
			resultMap.put(itemStrs[0], itemStrs[1]);
		}
		return resultMap;
	}
}
