package model;

import utils.FeatureBaseUtils.Orientation;

public class OpinionResult {

	public OpinionResult(Orientation type, String sentences) {
		super();
		this.type = type;
		this.sentences = sentences;
	}

	private Orientation type;
	private String sentences;

	@Override
	public String toString() {
		return type.ordinal() + ":" + sentences;
	}

}
