package model;

import java.util.List;

public class I_ComplexArray {

	private int position;
	private List<String> complexObject;
	private int HASH_CONST = 17;
	
	public I_ComplexArray(List<String> complexObject) {
		super();
		this.complexObject = complexObject;
	}

	public I_ComplexArray(int position, List<String> complexObject) {
		super();
		this.position = position;
		this.complexObject = complexObject;
	}

	public List<String> getComplexObject() {
		return complexObject;
	}

	public void setComplexObject(List<String> complexObject) {
		this.complexObject = complexObject;
	}

	@Override
	public int hashCode() {

		int hash_code = HASH_CONST;

		int childHash = complexObject == null ? 1 : 0;
		hash_code = HASH_CONST + 31 * childHash;
		hash_code = HASH_CONST + 31 * position;

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
		
		for (String s : i.getComplexObject()) {
			if (!complexObject.contains(s)) {
				return false;
			}
		}
		return true;
	}

}
