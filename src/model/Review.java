package model;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private List<Sentences> listSentences;
	private boolean isPositive;
	private String originalReview;

	private final String SEPERATOR = "./.";
	private final String BLANK = "";

	public List<Sentences> getListSentences() {
		return listSentences;
	}

	public void setListSentences(List<Sentences> listSentences) {
		this.listSentences = listSentences;
	}

	public boolean isPositive() {
		return isPositive;
	}

	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}

	public String getOriginalReview() {
		return originalReview;
	}

	public void setOriginalReview(String originalReview) {
		this.originalReview = originalReview;
	}

	@Override
	public String toString() {
		return null != originalReview ? originalReview : BLANK;
	}

	private List<Sentences> getSenTencesList() {
		List<Sentences> sentencesList = new ArrayList<>();
		Sentences sentences;

		if (null != originalReview && BLANK != originalReview) {

			String tempReview = new String(originalReview);
			while (originalReview.contains(SEPERATOR)) {

			}
		}
		return sentencesList;
	}
}
