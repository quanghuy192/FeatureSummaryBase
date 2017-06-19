package model;

import java.util.List;

import utils.GeneralUtil;

public class ComplexArray {

	private String[] complexObject;
	private int HASH_CONST = 17;

	public ComplexArray(String[] complexObject) {
		super();
		this.complexObject = complexObject;
	}

	public String[] getComplexObject() {
		return complexObject;
	}

	public void setComplexObject(String[] complexObject) {
		this.complexObject = complexObject;
	}

	@Override
	public int hashCode() {

		int hash_code = HASH_CONST;

		int childHash = complexObject == null ? 1 : 0;
		hash_code = HASH_CONST + 31 * childHash;

		return hash_code;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof ComplexArray)) {
			return false;
		}

		ComplexArray i = (ComplexArray) o;
		if (i.getComplexObject().length != complexObject.length) {
			return false;
		}
		List<String> parent = GeneralUtil.convertArrayToList(complexObject);
		for (String s : i.getComplexObject()) {
			if (!parent.contains(s)) {
				return false;
			}
		}
		return true;
	}

}
