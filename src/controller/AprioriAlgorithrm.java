package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Item;

/**
 * Apriori algorithm 
 * Find the pattern substring matching in parent string
 * (maybe optimization with Apriori-TID, Apriori-Habrid algorithm)
 * 
 * @author dqhuy
 *
 */
public class AprioriAlgorithrm {

	private String[][][] data = { { { "1" }, { "A", "C", "D" } },
								  { { "2" }, { "B", "C", "E" } },
								  { { "3" }, { "A", "B", "C", "E" } },
								  { { "4" }, { "B", "E" } }
								};
	
	private HashMap<Integer, List<String[]>> dataItems;
	private HashMap<Integer, List<String[]>> dataResultItems;
	private HashMap<Integer, List<String[]>> dataOriginalItems;
	
	private int N;
	private int step = 0;
	private double SUPPORT_MIN = 0.6;
	private int CONFIDENCE_MIN = 2;

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
		dataOriginalItems = dataItemsParent;
		if(step == 1){
			dataItemsParent = getAtomFirstData(dataItemsParent);
		}
		
		List<String[]> itemsParent = getOriginalItems();
		List<String[]> itemsChild = getItems(dataItemsParent);
		List<Item> itemsRule = new ArrayList<>();
		
		for (String[] child : itemsChild) {
			for (String[] parent : itemsParent) {
				Item i = new Item(parent, child);
				if (!itemsRule.contains(i)) {
					itemsRule.add(i);
				} else {
					Item clone = getItem(child, itemsRule);
					if (null != clone) {
						clone.setItemsParent(parent);
					}
				}
			}
		}

		int count = 0;
		for (Item i : itemsRule) {
			double percent = 1.0 * i.getQuantity() / N;
			List<String[]> subList;
			if (percent >= SUPPORT_MIN) {
				subList = new ArrayList<>();
				subList.add(i.getItemsChild());
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
		List<String[]> existList = new ArrayList<>();
		int count = 0;
		for (String[] s : itemList) {
			for (String a : itemAtom) {
				List<String> temp = convertArrayToList(s);
				temp.add(a);
				String[] subArr = convertListToArray(temp);
				if (!existList.contains(subArr)) {
					List<String[]> item = new ArrayList<>();
					item.add(subArr);
					dataItemsChild.put(count, item);
					existList.add(subArr);
					count++;
				}
			}
		}

		return dataItemsChild;
	}
	
	private List<String> convertArrayToList(String[] arr){
		List<String> temp = new ArrayList<>();
		for (String s : arr) {
			temp.add(s);
		}
		return temp;
	}
	
	private String[] convertListToArray(List<String> list){
		String[] temp = new String[list.size()];
		list.toArray(temp);
		return temp;
	}

	private Item getItem(String[] values, List<Item> items) {
		for (Item i : items) {
			Item temp = new Item(values);
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
	
	private List<String[]> getOriginalItems() {
		return getItems(dataOriginalItems);
	}

}
