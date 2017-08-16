package model;

public class Word {

	private final String BLANK = "";
	private final String SEPERATOR = "/";
	private int HASH_CONST = 17;

	private String word;
	private String type;
	private String originalWord;
	private String sentences;

	public Word() {
	}

	public Word(String value) {
		this.word = value;
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

	public String getSentences() {
		return sentences;
	}

	public void setSentences(String sentences) {
		this.sentences = sentences;
	}

	@Override
	public int hashCode() {

		int hash_code = HASH_CONST;

		int wordHash = word == null ? 1 : 0;
		int typeHash = type == null ? 1 : 0;
		int originalWordHash = originalWord == null ? 1 : 0;
		int sentencesHash = sentences == null ? 1 : 0;

		hash_code = HASH_CONST + 31 * wordHash;
		hash_code = HASH_CONST + 31 * typeHash;
		hash_code = HASH_CONST + 31 * originalWordHash;
		hash_code = HASH_CONST + 31 * sentencesHash;

		return hash_code;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof Word)) {
			return false;
		}

		Word i = (Word) o;

		return i.getWord().equalsIgnoreCase(word)
				/*&& i.getType().equalsIgnoreCase(type)
				&& i.getOriginalWord().equalsIgnoreCase(originalWord)
				&& i.getSentences().equalsIgnoreCase(sentences)*/;
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
					this.word = items[0].trim();
					this.type = items[1];
				}
			} else if (items.length >= 1) {
				if (!":".equalsIgnoreCase(items[0]) && !",".equalsIgnoreCase(items[0])) {
					this.word = items[0].trim();
				}
			}
		}
	}
}
