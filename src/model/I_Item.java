package model;

import java.util.List;

import utils.GeneralUtil;

public class I_Item {
	
	private int quantity;
	
	private List<Word> itemsParent;
	private List<Word> itemsChild;

	private int HASH_CONST = 17;
	
	public I_Item(List<Word> itemsChild) {
		super();
		this.itemsChild = itemsChild;
		quantity = 0;
	}

	public I_Item(List<Word> itemsParent, List<Word> itemsChild) {
		super();
		this.itemsParent = itemsParent;
		this.itemsChild = itemsChild;
		quantity = 0;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public List<Word> getItemsParent() {
		return itemsParent;
	}

	public void setItemsParent(List<Word> itemsParent) {
		this.itemsParent = itemsParent;
		
		if(GeneralUtil.checkSubArrayContain(itemsParent, itemsChild)){
			quantity++;
		}
	}

	public List<Word> getItemsChild() {
		return itemsChild;
	}

	public void setItemsChild(List<Word> itemsChild) {
		this.itemsChild = itemsChild;
	}

	@Override
	public int hashCode() {

		int hash_code = HASH_CONST;

		int childHash = itemsChild == null ? 1 : 0;
		int parentHash = itemsParent == null ? 1 : 0;
		hash_code = HASH_CONST + 31 * childHash;
		hash_code = HASH_CONST + 31 * parentHash;
		hash_code = HASH_CONST + 31 * quantity;

		return hash_code;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof I_Item)) {
			return false;
		}

		I_Item i = (I_Item) o;

		for (Word s : itemsChild) {
			if (!i.getItemsChild().contains(s)) {
				return false;
			}
		}
		return true;
	}
	
}
