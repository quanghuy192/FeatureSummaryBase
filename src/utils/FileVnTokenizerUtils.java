package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class FileVnTokenizerUtils {

	private FileReader reader;
	private FileWriter writer;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final String FILE_INPUT_NAME = "reviews_data.txt";
	private final String FILE_OUTPUT_NAME = "words_data.txt";
	private VietTokenizer tokenizer;

	private final String SEPERATOR_CHAR = "##########";
	private final String BLANK_CHAR = "";

	public FileVnTokenizerUtils() {
		try {
			tokenizer = new VietTokenizer();

			reader = new FileReader(new File(FILE_INPUT_NAME));
			writer = new FileWriter(new File(FILE_OUTPUT_NAME));

			bufferedReader = new BufferedReader(reader);
			bufferedWriter = new BufferedWriter(writer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String[]> readFileInput() {
		List<String[]> lineList = new ArrayList<String[]>();
		String s;
		try {
			while (null != (s = bufferedReader.readLine())) {
				String[] words = tokenizer.tokenize(s);

				// Remove space line and add words
				if (BLANK_CHAR.equals(words[0])) {
					continue;
				}
				lineList.add(words);
			}
			return lineList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader();
		}
		return lineList;
	}

	private void writeFileInput(List<String[]> wordsList) {
		try {
			for (String[] words : wordsList) {
				for (int i = 0; i < words.length; i++) {
					bufferedWriter.write(words[i]);
				}
				bufferedWriter.write("\n");
				bufferedWriter.write(SEPERATOR_CHAR);
				bufferedWriter.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWrite();
		}
	}

	public void generateWordData() {
		// read file
		List<String[]> in = readFileInput();
		// write file
		writeFileInput(in);
		// alert done
		System.out.println("Success !");
	}

	private void closeReader() {
		try {
			bufferedReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeWrite() {
		try {
			bufferedWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
