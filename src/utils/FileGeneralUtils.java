package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.VnPOS;

public class FileGeneralUtils {

	private FileReader reader;
	private BufferedReader bufferedReader;
	private final String FILE_INPUT_NAME = "treebank_vn.txt";

	public FileGeneralUtils() {
		try {
			reader = new FileReader(new File(FILE_INPUT_NAME));
			bufferedReader = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<VnPOS> getVnPOS_ListData() {
		List<VnPOS> lineList = new ArrayList<VnPOS>();
		String s;
		try {
			while (null != (s = bufferedReader.readLine())) {

				// Remove space line and add words
				lineList.add(new VnPOS(s));
			}
			return lineList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return lineList;
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
