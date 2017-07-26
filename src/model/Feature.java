package model;

import java.util.ArrayList;
import java.util.List;

public class Feature {
	
	private String feature;
	private List<String> adjectiveList;
	private List<String> opinionWords;
	private List<Word> opinionSentences;
	private List<String> positiveSentences;
	private List<String> negativeSentences;
	
	public Feature() {
		adjectiveList = new ArrayList<>();
		opinionWords = new ArrayList<>();
		opinionSentences = new ArrayList<>();
		positiveSentences = new ArrayList<>();
		negativeSentences = new ArrayList<>();
	}
	
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public List<String> getAdjectiveList() {
		return adjectiveList;
	}
	public void setAdjectiveList(List<String> adjectiveList) {
		this.adjectiveList = adjectiveList;
	}
	public List<String> getOpinionWords() {
		return opinionWords;
	}
	public void setOpinionWords(List<String> opinionWords) {
		this.opinionWords = opinionWords;
	}
	public List<Word> getOpinionSentences() {
		return opinionSentences;
	}
	public void setOpinionSentences(List<Word> opinionSentences) {
		this.opinionSentences = opinionSentences;
	}
	public List<String> getPositiveSentences() {
		return positiveSentences;
	}
	public void setPositiveSentences(List<String> positiveSentences) {
		this.positiveSentences = positiveSentences;
	}
	public List<String> getNegativeSentences() {
		return negativeSentences;
	}
	public void setNegativeSentences(List<String> negativeSentences) {
		this.negativeSentences = negativeSentences;
	}
	
}
