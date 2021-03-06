package old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ComplexArray;
import model.Item;
import utils.GeneralUtil;

/**
 * Apriori algorithm Find the pattern substring matching in parent string (maybe
 * optimization with Apriori-TID, Apriori-Habrid algorithm)
 * 
 * Optimization
 * 
 * 1) L1 = {large 1-itemsets}; 
 * 2) for (k=2; Lk-1 ≠ ∅ ; k++) 
 * 3) { 
 * 4)     Ck=apriori-gen(Lk-1); //generate new candidate itemsets 
 * 5) for all transactions t∈D and t.delete=0 
 * 6) { 
 * 7)     if t.count<k then // if the size of transaction t is less than k, t is useless for Ck generated 
 * 8)     t.delete=1  //mark t the deleting tag to skip over the record in next database scanning
 * 9) 	  else 
 * 10) { 
 * 11) Ct=subset(Ck,t); //candidate itemsets contained in transaCion t 
 * 12) if Ct = ∅ then // if t does not contain any subset of candidate itemsets Ck, mark the deleting tag 
 * 13) 	  t.delete=1; 
 * 14) else 
 * 15) {
 * 16) for all candidates c∈Ct 
 * 17) c.count++; 
 * 18) } 
 * 19) } 
 * 20) } 
 * 21) Lk={c∈Ck|c.count ≥ minsup} 
 * 22) } 
 * 23) Answer= kLk;
 * 
 * @author dqhuy
 *
 */
public class AprioriAlgorithrm {

	private HashMap<Integer, List<String[]>> dataResultItems;
	private HashMap<Integer, List<String[]>> dataOriginalItems;
	
	private int N;
	private int step = 0;
	private double SUPPORT_MIN = 0.01;
	private int CONFIDENCE_MIN = 2;

	public AprioriAlgorithrm() {
		dataResultItems = new HashMap<>();
	}

	public HashMap<Integer, List<String[]>> generate_K_ItemSet(HashMap<Integer, List<String[]>> dataItemsParent) {

		step++;
		N = dataItemsParent.size();
		
		List<String[]> itemsChild = null;
		List<Item> itemsRule = new ArrayList<>();
		
		if(step == 1){
			dataOriginalItems = dataItemsParent;
			dataItemsParent = getAtomFirstData(dataItemsParent);
			itemsChild = getItems(dataItemsParent);
		}else{
			itemsChild = new ArrayList<>();
			for (Map.Entry<Integer, List<String[]>> map : dataItemsParent.entrySet()) {
				itemsChild.add(map.getValue().get(0));
			}
		}
		List<String[]> itemsParent = getOriginalItems();
		
		for (String[] child : itemsChild) {
			for (String[] parent : itemsParent) {
				Item i = new Item(parent, child);
				if (!itemsRule.contains(i)) {
					i.setItemsParent(parent);
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
		dataResultItems.clear();
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
		List<ComplexArray> existList = new ArrayList<>();
		int count = 0;
		for (String[] s : itemList) {
			for (String a : itemAtom) {
				
				List<String> temp = GeneralUtil.convertArrayToList(s);
				if(temp.contains(a)){
					continue;
				}
				
				temp.add(a);
				String[] subArr = GeneralUtil.convertListToArray(temp);
				ComplexArray complex = new ComplexArray(subArr);
				
				if (!existList.contains(complex)) {
					List<String[]> item = new ArrayList<>();
					item.add(subArr);
					dataItemsChild.put(count, item);
					existList.add(complex);
					count++;
				}
			}
		}

		return dataItemsChild;
	}

	private Item getItem(String[] child, List<Item> items) {
		for (Item i : items) {
			Item temp = new Item(i.getItemsParent(),child);
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
