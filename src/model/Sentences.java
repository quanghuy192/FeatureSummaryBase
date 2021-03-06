package model;

import java.util.ArrayList;
import java.util.List;

public class Sentences {

	private List<Word> listWord;
	private boolean isPositive;
	private String originalSentences;

	private final String BLANK = "";
	private final String SEPERATOR = " ";
	private final String SPLASH = "/";

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

			int i = 0;
			while (i < items.length) {
				word = new Word();
				if (items[i].contains(SPLASH)) {
					word.setOriginalWord(items[i]);
					i++;
				} else {
					int s = 0;
					StringBuilder builder = new StringBuilder(items[i]);
					while (!builder.toString().contains(SPLASH)) {
						s++;
						builder.append(SEPERATOR).append(items[i + s]);
					}
					word.setOriginalWord(builder.toString());
					i += s + 1;
				}
				word.setSentences(getOriginalSentences());
				wordList.add(word);
			}
		}

		return wordList;
	}
}
