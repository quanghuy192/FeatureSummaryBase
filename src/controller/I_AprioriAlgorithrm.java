package controller;

import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;
import model.I_Item;

/**
 * Apriori algorithm Find the pattern substring matching in parent string (maybe
 * optimization with Apriori-TID, Apriori-Habrid algorithm)
 * 
 * Optimization
 * {@link https://www.academia.edu/6823564/The_Optimization_and_Improvement_of_the_Apriori_Algorithm}
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
public class I_AprioriAlgorithrm {

	private List<I_ComplexArray> dataResultItems;
	private List<I_ComplexArray> dataOriginalItems;
	
	private int N;
	private int step = 0;
	private double SUPPORT_MIN = 2;
	private int CONFIDENCE_MIN = 2;

	public I_AprioriAlgorithrm() {
		dataResultItems = new ArrayList<>();
	}

	public List<I_ComplexArray> generate_K_ItemSet(List<I_ComplexArray> dataItemsParent) {

		step++;
		N = dataItemsParent.size();

		List<I_Item> itemsRule = new ArrayList<>();
		List<I_ComplexArray> itemsChild;

		// Create large 1-itemsets
		if (step == 1) {
			dataOriginalItems = dataItemsParent;
			dataItemsParent = getAtomFirstData(dataItemsParent);
		}
		itemsChild = getItems(dataItemsParent);

		for (I_ComplexArray child : itemsChild) {
			for (I_ComplexArray parent : dataOriginalItems) {
				I_Item i = new I_Item(parent.getComplexObject(), child.getComplexObject());
				if (!itemsRule.contains(i)) {
					i.setItemsParent(parent.getComplexObject());
					itemsRule.add(i);
				} else {
					I_Item clone = getItem(child.getComplexObject(), itemsRule);
					if (null != clone) {
						clone.setItemsParent(parent.getComplexObject());
					}
				}
			}
		}

		int count = 0;
		dataResultItems.clear();
		for (I_Item i : itemsRule) {
			double percent = 1.0 * i.getQuantity();
			List<String> subList;
			if (percent >= SUPPORT_MIN) {
				subList = i.getItemsChild();
				I_ComplexArray com = new I_ComplexArray(count, subList);
				dataResultItems.add(com);
				count++;
			}
		}

		List<I_ComplexArray> dataItemsChild = getItemsChild(dataResultItems);
		if (dataItemsChild.size() > 0) {
			return generate_K_ItemSet(dataItemsChild);
		} else {
			return dataItemsChild;
		}
	}

	private List<I_ComplexArray> getAtomFirstData(List<I_ComplexArray> dataItemsParent) {
		List<I_ComplexArray> itemsFirst = new ArrayList<>();
		List<String> atomItems = getAtomItems(dataItemsParent);
		I_ComplexArray com;
		List<String> itemString;
		for (String s : atomItems) {
			itemString = new ArrayList<>();
			itemString.add(s);
			com = new I_ComplexArray(itemString);
			itemsFirst.add(com);
		}
		return itemsFirst;
	}

	private List<I_ComplexArray> getItemsChild(List<I_ComplexArray> items) {
		List<I_ComplexArray> dataItemsChild = new ArrayList<>();
		List<I_ComplexArray> itemList = getItems(items);
		List<String> itemAtom = getAtomItems(items);
		for (I_ComplexArray s : itemList) {
			for (String a : itemAtom) {
				
				List<String> temp = s.getComplexObject();
				if(temp.contains(a)){
					continue;
				}
				
				temp.add(a);
				I_ComplexArray complex = new I_ComplexArray(temp);
				
				if (!dataItemsChild.contains(complex)) {
					dataItemsChild.add(complex);
				}
			}
		}
		return dataItemsChild;
	}

	private I_Item getItem(List<String> child, List<I_Item> items) {
		for (I_Item i : items) {
			I_Item temp = new I_Item(child);
			if (i.equals(temp)) {
				return i;
			}
		}
		return null;
	}

	private List<I_ComplexArray> getItems(List<I_ComplexArray> dataItemsParent) {
		List<I_ComplexArray> itemList = new ArrayList<>();
		for (I_ComplexArray i : dataItemsParent) {
			if (!itemList.contains(i)) {
				itemList.add(i);
			}
		}
		return itemList;
	}
	
	private List<String> getAtomItems(List<I_ComplexArray> dataItemsParent) {
		List<String> itemList = new ArrayList<>();
		for (I_ComplexArray com : dataItemsParent) {
			List<String> itemValue = com.getComplexObject();
			for (String s : itemValue) {
				if (!itemList.contains(s)) {
					itemList.add(s);
				}
			}
		}
		return itemList;
	}
}
