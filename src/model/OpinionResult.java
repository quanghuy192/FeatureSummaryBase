package model;

import utils.FeatureBaseUtils.Orientation;

public class OpinionResult {

	private final String SPLASH_CHAR = "/";
	private final String BLANK = " ";
	private String feature;

	public OpinionResult(Orientation type, String sentences, String feature) {
		super();
		this.type = type;
		this.sentences = sentences;
		this.feature = feature;
		replaceRedundantChar();
	}

	private void replaceRedundantChar() {
		StringBuilder builder = new StringBuilder();
		String[] arr = sentences.split(BLANK);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains(SPLASH_CHAR)) {
				arr[i] = arr[i].split(SPLASH_CHAR)[0];
			}
			builder.append(arr[i] + BLANK);
		}
		sentences = builder.toString();
	}

	private Orientation type;
	private String sentences;

	@Override
	public String toString() {
		return sentences;
	}

	public Orientation getType() {
		return type;
	}

	public void setType(Orientation type) {
		this.type = type;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

}
