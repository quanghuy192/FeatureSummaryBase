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

import model.Review;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.hus.nlp.utils.UTF8FileUtility;

public class WordUtils {

	private FileReader reader;
	private FileWriter writer;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final String FILE_OUTPUT_NAME = "words_with_tags.txt";
	private final String FILE_INPUT_NAME = "reviews_data.txt";

	public WordUtils() {
		try {
			writer = new FileWriter(new File(FILE_OUTPUT_NAME));
			bufferedWriter = new BufferedWriter(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> readFileInput() {
		List<String> wordList = new ArrayList<String>();

		String data = FILE_INPUT_NAME;
		String[] sentences = UTF8FileUtility.getLines(data);

		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		for (String sentence : sentences) {
			try {
				String item = tagger.tagText(sentence);
				if (item.length() > 0) {
					wordList.add(item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return wordList;
	}

	private void writeFileInput(List<String> wordsList) {
		try {
			for (String words : wordsList) {
				bufferedWriter.write(words);
				bufferedWriter.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWrite();
		}
	}

	public synchronized void generateWordTagData() {
		// read file
		List<String> in = readFileInput();
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

	public List<Review> getReviewList() {

		// Create data
		generateWordTagData();

		// Open data
		openWordTagData();

		List<Review> reviewList = new ArrayList<Review>();
		String s;
		try {
			while (null != (s = bufferedReader.readLine())) {
				Review review = new Review();
				review.setOriginalReview(s);
				reviewList.add(review);
			}
			return reviewList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader();
		}
		return reviewList;
	}

	private void openWordTagData() {
		try {
			reader = new FileReader(new File(FILE_OUTPUT_NAME));
			bufferedReader = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
