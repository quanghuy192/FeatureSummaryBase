package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.I_ComplexArray;
import model.Review;
import model.Sentences;
import model.Word;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.hus.nlp.utils.UTF8FileUtility;

public class WordUtils {

	private FileReader reader;
	private FileWriter writer;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final String FILE_OUTPUT_NAME = "words_with_tags.txt";
	private final String FILE_INPUT_NAME = "reviews_data.txt";
	// private final String FEATURE_BASE_RAW_FILE = "feature_base_raw.txt";
	private final String BLANK = " ";

	private List<String> nounTagList;

	public WordUtils() {
		try {
			nounTagList = new ArrayList<>();
			nounTagList.add("N");
			// nounTagList.add("Np");
			// nounTagList.add("Nc");
			// nounTagList.add("Nu");
			nounTagList.add("NP");

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

	// Generate feature base raw file
	public List<I_ComplexArray> generateFeatureBase() {

		// open and create new output file
		// FileWriter mWriter = null;
		// BufferedWriter mBufferedWriter = null;
		// try {
		// mWriter = new FileWriter(new File(FEATURE_BASE_RAW_FILE));
		// mBufferedWriter = new BufferedWriter(mWriter);
		List<I_ComplexArray> featureBases = new ArrayList<>();
		List<Word> featureList;
		I_ComplexArray complexArray;

		List<Review> listReview = getReviewList();
		for (Review r : listReview) {

			featureList = new ArrayList<>();
			List<Sentences> sentences = r.getListSentences();
			for (Sentences s : sentences) { 

				List<Word> words = s.getListWord();
				for (Word w : words) {
					if (nounTagList.contains(w.getType()) && BLANK != w.getWord()) {
						// mBufferedWriter.write(w.getWord() + BLANK);
						// System.out.print(w.getWord() + BLANK);
						featureList.add(w);
					}
				}
			}

			complexArray = new I_ComplexArray(featureList);
			featureBases.add(complexArray);
			// System.out.println();
			// mBufferedWriter.write("\n");
			// mBufferedWriter.write("\n");
		}
		// mBufferedWriter.close();
		// mWriter.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// System.out.println("Done !!!!!!!");
		// }
		return featureBases;
	}
}
