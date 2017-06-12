package model;

import java.util.ArrayList;
import java.util.List;

public class Sentences {

	private List<Word> listWord;
	private boolean isPositive;
	private String originalSentences;

	private final String BLANK = "";
	private final String SEPERATOR = " ";

	public List<Word> getListWord() {
		return listWord;
	}

	public void setListWord(List<Word> listWord) {
		this.listWord = listWord;
	}

	public boolean isPositive() {
		return isPositive;
	}

	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}

	public String getOriginalSentences() {
		return originalSentences;
	}

	public void setOriginalSentences(String originalSentences) {
		this.originalSentences = originalSentences;

		// convert original sentences to word list
		setListWord(createWordList());
	}

	@Override
	public String toString() {
		return null != originalSentences ? originalSentences : BLANK;
	}

	private List<Word> createWordList() {

		List<Word> wordList = new ArrayList<>();
		Word word;

		if (null != originalSentences && BLANK != originalSentences) {

			String[] items = originalSentences.split(SEPERATOR);

			for (int i = 0; i < items.length; i++) {
				word = new Word();
				word.setOriginalWord(items[i]);
				wordList.add(word);
			}
		}

		return wordList;
	}
}
