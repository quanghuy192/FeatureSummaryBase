package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AprioriAlgorithrm {

	private List<String> itemList;
	private String[][][] data = { { { "1" }, { "A", "C", "D" } },
								  { { "2" }, { "B", "C", "E" } },
								  { { "3" }, { "A", "B", "C", "E" } },
								  { { "4" }, { "B", "E" } }
								};
	
	private HashMap<Integer, List<String>> dataItems;
	private HashMap<Integer, List<String>> dataResultItems;
	private List<String[]> candidateItemList;
	
	private int N;
	private int SUPPORT_MIN = 2;
	private int CONFIDENCE = 2;

	public AprioriAlgorithrm() {
		itemList = new ArrayList<>();
		dataItems = new HashMap<>();
		dataResultItems = new HashMap<>();
		candidateItemList = new ArrayList<>();
		
		N = data.length;

		for (int i = 0; i < data.length; i++) {
			String[][] items = data[i];
			dataItems.put(i, Arrays.asList(items[1]));
		}
	}

	private void generateK_ItemSet(HashMap<Integer, List<String>> dataItemsParent) {

		List<String> itemList = getItems(dataItemsParent);
		List<Item> itemsRule = new ArrayList<>();
		for (String s : itemList) {
			if(!itemsRule.contains(s)){
				// itemsRule.add(new Item(s, 1));
			}else{
				Item i = getItem(s,itemsRule);
				if(null != i && i.getQuantity() > -1){
					int quantity = i.getQuantity();
					i.setQuantity(quantity++);
					itemsRule.add(i);
				}
			}
		}
		
		for (Item i : itemsRule) {
			int percent = i.getQuantity()/N;
			if(percent >= SUPPORT_MIN){
				
			}
		}
	}

	private Item getItem(String value, List<Item> items) {
		for (Item i : items) {
			if (i.getValue().equals(value)) {
				return i;
			}
		}
		return null;
	}

	private List<String> getItems(HashMap<Integer, List<String>> dataItemsParent) {
		List<String> itemList = new ArrayList<>();
		for (Map.Entry<Integer, List<String>> map : dataItemsParent.entrySet()) {
			List<String> itemValue = map.getValue();
			for (String s : itemValue) {
				if(!itemList.contains(s)){
					itemList.add(s);
				}
			}
		}
		return itemList;
	}


	private class Item{
		private String[] value;
		private int quantity;
		
		private int HASH_CONST = 17;
		
		public Item(String[] value, int quantity) {
			super();
			this.value = value;
			this.quantity = quantity;
		}
		
		public String[] getValue() {
			return value;
		}

		public void setValue(String[] value) {
			this.value = value;
		}

		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		@Override
		public int hashCode() {

			int hash_code = HASH_CONST;

			int valueHash = value == null ? 1 : 0;
			hash_code = HASH_CONST + 31 * valueHash;
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

			if (i.getValue().length != value.length) {
				return false;
			}

			for (int j = 0; j < value.length; j++) {
				if (!value[j].equals(i.getValue()[j])) {
					return false;
				}
			}
			return true;
		}
	}
}
