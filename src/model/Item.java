package model;

import utils.GeneralUtil;

public class Item {
	
	private int quantity;
	
	private String[] itemsParent;
	private String[] itemsChild;

	private int HASH_CONST = 17;
	
	public Item(String[] itemsChild) {
		super();
		this.itemsChild = itemsChild;
		quantity = 0;
	}

	public Item(String[] itemsParent, String[] itemsChild) {
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
	
	public String[] getItemsParent() {
		return itemsParent;
	}

	public void setItemsParent(String[] itemsParent) {
		this.itemsParent = itemsParent;
		
		if(GeneralUtil.checkSubArrayContain(itemsParent, itemsChild)){
			quantity++;
		}
	}

	public String[] getItemsChild() {
		return itemsChild;
	}

	public void setItemsChild(String[] itemsChild) {
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
		if (!(o instanceof Item)) {
			return false;
		}

		Item i = (Item) o;

		for (int j = 0; j < itemsChild.length; j++) {
			if (!itemsChild[j].equals(i.getItemsChild()[j])) {
				return false;
			}
		}
		return true;
	}
	
}
