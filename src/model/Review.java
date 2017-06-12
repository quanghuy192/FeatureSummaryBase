package model;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private List<Sentences> listSentences;
	private boolean isPositive;
	private String originalReview;

	private final String SEPERATOR = "\\./.";
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

		// convert original review to sentences list
		setListSentences(createSentencesList());
	}

	@Override
	public String toString() {
		return null != originalReview ? originalReview : BLANK;
	}

	private List<Sentences> createSentencesList() {

		List<Sentences> sentencesList = new ArrayList<>();
		Sentences sentences;
		
		if (null != originalReview && BLANK != originalReview) {
			
			String[] reviewSentences = originalReview.split(SEPERATOR);
			
			for (int i = 0; i < reviewSentences.length; i++) {
				sentences = new Sentences();
				sentences.setOriginalSentences(reviewSentences[i]);
				sentencesList.add(sentences);
			}
		}
		
		return sentencesList;
	}
}
