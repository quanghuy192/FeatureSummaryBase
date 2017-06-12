package model;

public class Word {

	private final String BLANK = "";
	private final String SEPERATOR = "/";

	private String word;
	private String type;
	private String originalWord;

	public Word() {
	}

	public Word(String value) {
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

	public String getOriginalWord() {
		return originalWord;
	}

	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;

		// convert word
		createWord();
	}

	@Override
	public String toString() {
		if (null != word && null != type) {
			return new StringBuilder(word).append("-").append(type).toString();
		} else {
			return BLANK;
		}
	}

	private void createWord() {
		if (null != originalWord && BLANK != originalWord) {
			String[] items = originalWord.split(SEPERATOR);
			if (items.length >= 2) {
				if (!":".equalsIgnoreCase(items[0]) && !",".equalsIgnoreCase(items[0])) {
					this.word = items[0];
					this.type = items[1];
				}
			}
		}
	}
}
