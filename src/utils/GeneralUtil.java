package utils;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtil {
	
	/**
	 * private constructor
	 * */
	private GeneralUtil() {
	}
	
	public static List<String> convertArrayToList(String[] arr) {
		List<String> temp = new ArrayList<>();
		for (String s : arr) {
			temp.add(s);
		}
		return temp;
	}
	
	/**
	 * 
	 * Blute-Force algorithm
	 * Find the pattern substring matching in parent string
	 * (maybe optimization with KMP, Boyer-Moore algorithm)  
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	/*public boolean checkSubArrayContain(String[] parent, String[] child) {
		for (int i = 0; i < parent.length; i++) {
			int j = i;
			int s = 0;
			while (s < child.length) {
				if (child[s].equalsIgnoreCase(parent[j])) {
					s++;
					j++;
					if (s == child.length) {
						return true;
					}
				} else {
					break;
				}
			}
		}
		return false;
	}*/
	
	public static boolean checkSubArrayContain(String[] parent, String[] child) {
		List<String> parentList = convertArrayToList(parent);
		for (String s : child) {
			if (!parentList.contains(s)) {
				return false;
			}
		}
		return true;
	}
	
	public static String[] convertListToArray(List<String> list){
		String[] temp = new String[list.size()];
		list.toArray(temp);
		return temp;
	}

}
