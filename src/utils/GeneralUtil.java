package utils;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtil {
	
	private static long startTime;
	private static long endTime;
	
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
	
	public static boolean checkSubArrayContain(List<String> parent, List<String> child) {
		for (String s : child) {
			if (!parent.contains(s)) {
				return false;
			}
		}
		return true;
	}
	
	public static String[] convertListToArray(List<String> list) {
		String[] temp = new String[list.size()];
		list.toArray(temp);
		return temp;
	}

	public static void setTimeStart() {
		startTime = System.nanoTime();
	}

	public static void setTimeEnd() {
		endTime = System.nanoTime();
		System.out.println("Took " + (1.0 * endTime/(1000000000) - 1.0 * startTime/(1000000000)) + " s");
	}

}
