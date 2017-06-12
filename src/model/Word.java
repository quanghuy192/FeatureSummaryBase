package model;

public class Word {

	private final String BLANK = "";

	private String word;
	private String type;

	public Word() {
	}

	public Word(String value) {
		String[] values = value.split("/");
		if (values.length >= 2) {
			if (!":".equalsIgnoreCase(values[0]) && !",".equalsIgnoreCase(values[0])) {
				this.word = values[0];
				this.type = values[1];
			}
		}
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (null != word && null != type) {
			return new StringBuilder(word).append("-").append(type).toString();
		} else {
			return BLANK;
		}
	}

}
