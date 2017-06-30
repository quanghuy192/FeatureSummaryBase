package model;

import java.util.ArrayList;
import java.util.List;

public class I_ComplexArray implements Cloneable {

	private int position;
	private List<Word> complexObject;
	private int HASH_CONST = 17;
	private boolean deleteTag;
	private String sentences;
	
	public I_ComplexArray() {
		super();
	}

	public I_ComplexArray(List<Word> complexObject) {
		super();
		this.complexObject = complexObject;
		deleteTag = false;
	}

	public I_ComplexArray(int position, List<Word> complexObject) {
		super();
		this.position = position;
		this.complexObject = complexObject;
		deleteTag = false;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<Word> getComplexObject() {
		return complexObject;
	}

	public void setComplexObject(List<Word> complexObject) {
		this.complexObject = complexObject;
	}
	
	public boolean isDeleteTag() {
		return deleteTag;
	}

	public void setDeleteTag(boolean deleteTag) {
		this.deleteTag = deleteTag;
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

		int childHash = complexObject == null ? 1 : 0;
		int sentencesHash = sentences == null ? 1 : 0;
		hash_code = HASH_CONST + 31 * childHash;
		hash_code = HASH_CONST + 31 * position;
		hash_code = HASH_CONST + 31 * sentencesHash;

		return hash_code;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof I_ComplexArray)) {
			return false;
		}

		I_ComplexArray i = (I_ComplexArray) o;
		if (i.getComplexObject().size() != complexObject.size()) {
			return false;
		}
		
		for (Word s : i.getComplexObject()) {
			if (!complexObject.contains(s)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public I_ComplexArray clone() throws CloneNotSupportedException {
		
		// Deep clone
		List<Word> list = new ArrayList<Word>();
		for (Word s : this.getComplexObject()) {
			Word w = new Word();
			w.setWord(s.getWord());
			w.setType(s.getType());
			w.setOriginalWord(s.getOriginalWord());
			w.setSentences(s.getSentences());
			list.add(w);
		}
		
		I_ComplexArray complexArray = null;
		try {
			complexArray = (I_ComplexArray) super.clone();
			complexArray.setComplexObject(list);
			complexArray.position = getPosition();
			complexArray.setDeleteTag(isDeleteTag());

			return complexArray;
		} catch (CloneNotSupportedException e) {
			new AssertionError();
		}
		return complexArray;
	}

}
