package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.I_ComplexArray;

public class FeatureBaseUtils {

	private FileReader reader;
	private BufferedReader bufferedReader;
	// private final String FEATURE_BASE_RAW_FILE = "feature_base_raw.txt";
	// private final String BLANK = " ";

	public FeatureBaseUtils() {
		// try {
		// reader = new FileReader(new File(FEATURE_BASE_RAW_FILE));
		// bufferedReader = new BufferedReader(reader);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	// generate feature base after filter
	public List<I_ComplexArray> featureBase() {

		// HashMap<Integer, List<String[]>> featureBases = new HashMap<>();

		// read data include noun & noun phase
		// List<String[]> lineList = new ArrayList<String[]>();
		// String s;
		// int count = 0;
		// try {
		// while (null != (s = bufferedReader.readLine())) {
		// String[] wordList = s.split(BLANK);
		// lineList.add(wordList);
		// featureBases.put(count, lineList);
		// count++;
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// close();
		// }

		return new WordUtils().generateFeatureBase();
	}

	private void close() {
		try {
			bufferedReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
