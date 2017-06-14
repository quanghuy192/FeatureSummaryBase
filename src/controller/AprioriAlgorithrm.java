package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AprioriAlgorithrm {

	private String[][][] data = { { { "1" }, { "A", "C", "D" } },
								  { { "2" }, { "B", "C", "E" } },
								  { { "3" }, { "A", "B", "C", "E" } },
								  { { "4" }, { "B", "E" } }
								};
	
	private HashMap<Integer, List<String[]>> dataItems;
	private HashMap<Integer, List<String[]>> dataResultItems;
	
	private int N;
	private int step = 0;
	private int SUPPORT_MIN = 2;
	private int CONFIDENCE = 2;

	public AprioriAlgorithrm() {
		dataItems = new HashMap<>();
		dataResultItems = new HashMap<>();

		N = data.length;
		List<String[]> datas;
		for (int i = 0; i < data.length; i++) {
			String[][] items = data[i];
			datas = new ArrayList<>();
			datas.add(items[1]);
			dataItems.put(i, datas);
		}
	}

	public HashMap<Integer, List<String[]>> generate_K_ItemSet(HashMap<Integer, List<String[]>> dataItemsParent) {

		step++;
		if(step == 1){
			dataItemsParent = getAtomFirstData(dataItemsParent);
		}
		
		List<String[]> itemList = getItems(dataItemsParent);
		List<Item> itemsRule = new ArrayList<>();
		for (String[] s : itemList) {
			if (!itemsRule.contains(s)) {
				itemsRule.add(new Item(s, 1));
			} else {
				Item i = getItem(s, itemsRule);
				if (null != i && i.getQuantity() > -1) {
					int quantity = i.getQuantity();
					i.setQuantity(quantity++);
					itemsRule.add(i);
				}
			}
		}

		for (Item i : itemsRule) {
			int percent = i.getQuantity() / N;
			int count = 0;
			List<String[]> subList;
			if (percent >= SUPPORT_MIN) {
				subList = new ArrayList<>();
				subList.add(i.getValue());
				dataResultItems.put(count, subList);
				count++;
			}
		}

		HashMap<Integer, List<String[]>> dataItemsChild = getItemsChild(dataResultItems);
		if (dataItemsChild.size() > 0) {
			return generate_K_ItemSet(dataItemsChild);
		} else {
			return dataItemsChild;
		}
	}

	private HashMap<Integer, List<String[]>> getAtomFirstData(HashMap<Integer, List<String[]>> dataItemsParent) {
		HashMap<Integer, List<String[]>> itemsFirst = new HashMap<>();
		List<String> atomItems = getAtomItems(dataItemsParent);
		int count = 0;
		List<String[]> list;
		for (String s : atomItems) {
			list = new ArrayList<>();
			list.add(new String[] { s });
			itemsFirst.put(count, list);
			count++;
		}
		return itemsFirst;
	}

	private HashMap<Integer, List<String[]>> getItemsChild(HashMap<Integer, List<String[]>> items) {
		HashMap<Integer, List<String[]>> dataItemsChild = new HashMap<>();
		List<String[]> itemList = getItems(items);
		List<String> itemAtom = getAtomItems(items);
		List<Object[]> existList = new ArrayList<>();
		int count = 0;
		for (String[] s : itemList) {
			for (String a : itemAtom) {
				List<String> temp = Arrays.asList(s);
				temp.add(a);
				if (!existList.contains(temp.toArray())) {
					List<String[]> item = new ArrayList<>();
					item.add((String[]) temp.toArray());
					dataItemsChild.put(count, item);
				}
			}
		}

		return dataItemsChild;
	}

	private Item getItem(String[] value, List<Item> items) {
		for (Item i : items) {
			Item temp = new Item(value, i.getQuantity());
			if (i.equals(temp)) {
				return i;
			}
		}
		return null;
	}

	private List<String[]> getItems(HashMap<Integer, List<String[]>> dataItemsParent) {
		List<String[]> itemList = new ArrayList<>();
		for (Map.Entry<Integer, List<String[]>> map : dataItemsParent.entrySet()) {
			List<String[]> itemValue = map.getValue();
			for (String[] s : itemValue) {
				if(!itemList.contains(s)){
					itemList.add(s);
				}
			}
		}
		return itemList;
	}
	
	private List<String> getAtomItems(HashMap<Integer, List<String[]>> dataItemsParent) {
		List<String> itemList = new ArrayList<>();
		for (Map.Entry<Integer, List<String[]>> map : dataItemsParent.entrySet()) {
			List<String[]> itemValue = map.getValue();
			for (String[] s : itemValue) {
				for (String subS : s) {
					if(!itemList.contains(subS)){
						itemList.add(subS);
					}
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
