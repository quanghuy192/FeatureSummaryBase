package model;

import java.util.List;

public class Sentences {

	private List<Word> listWord;
	private boolean isPositive;
	private String originalSentences;

	private final String SEPERATOR = "./.";

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
	}

}
