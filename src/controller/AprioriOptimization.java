package controller;

import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;
import model.I_Item;
import model.Word;
import utils.GeneralUtil;

/**
 * Apriori algorithm Find the pattern substring matching in parent string (maybe
 * optimization with Apriori-TID, Apriori-Habrid algorithm)
 * 
 * Optimization
 * {@link https://www.academia.edu/6823564/The_Optimization_and_Improvement_of_the_Apriori_Algorithm}
 * 
 * 1) L1 = {large 1-itemsets}; 2) for (k=2; Lk-1 ≠ ∅ ; k++) 3) { 4)
 * Ck=apriori-gen(Lk-1); // generate new candidate itemsets 5) for all
 * transactions t∈D and t.delete=0 6) { 7) if t.count<k then // if the size of
 * transaction t is less than k, t is useless for Ck generated 8) t.delete=1 //
 * mark t the deleting tag to skip over the record in next database scanning 9)
 * else 10) { 11) Ct=subset(Ck,t); // candidate itemsets contained in transaCion
 * t 12) if Ct = ∅ then // if t does not contain any subset of candidate
 * itemsets Ck, mark the deleting tag 13) t.delete=1; 14) else 15) { 16) for all
 * candidates c∈Ct 17) c.count++; 18) } 19) } 20) } 21) Lk={c∈Ck|c.count ≥
 * minsup} 22) } 23) Answer= kLk;
 * 
 * @author dqhuy
 *
 */
public class AprioriOptimization {

	private List<I_ComplexArray> dataOriginalItems;
	private List<I_ComplexArray> dataResultItems;
	private int N;
	private double SUPPORT_MIN = 0.02;
	private List<I_Item> itemsRuleAll;

	public AprioriOptimization() {
		dataResultItems = new ArrayList<>();
		itemsRuleAll = new ArrayList<>();
	}

	public List<I_ComplexArray> generate_K_ItemSet(List<I_ComplexArray> dataItemsParent) {

		GeneralUtil.setTimeStart();
		dataOriginalItems = dataItemsParent;
		List<I_ComplexArray> dataResultItemsClone = new ArrayList<>();

		N = dataOriginalItems.size();

		// L1 = {large 1-itemsets};
		List<I_ComplexArray> candidate = getAtomFirstData(dataItemsParent);
		dataResultItems = candidate;

		if (dataResultItems.size() > 1) {
			show(dataResultItems);
			GeneralUtil.setTimeEnd();
			System.out.println("Support min = " + SUPPORT_MIN);
			System.out.println("Count : " + dataResultItems.size() + " items");
			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
		}

		// for (k=2; Lk-1 ≠ ∅ ; k++)
		for (int k = 2; candidate.size() > 0; k++) {

			// Ck=apriori-gen(Lk-1);
			candidate = join(dataResultItems, candidate);// generate new candidate itemsets
			candidate = GeneralUtil.pruneDuplicateComplex(candidate);

			itemsRuleAll.clear();

			// for all transactions t∈D and t.delete=0
			for (I_ComplexArray c : dataOriginalItems) {
				// for all transactions t∈D and t.delete=0
				if (!c.isDeleteTag()) {
					// if t.count<k then // if the size of transaction t is less than k, t is
					// useless for Ck generated
					// t.delete=1 //mark t the deleting tag to skip over the record in next database
					// scanning
					if (dataOriginalItems.size() < k) {
						c.setDeleteTag(true);
					} else {
						// Ct=subset(Ck,t); //candidate itemsets contained in transaCion t
						System.out.println("Parent = " + c.toString());
						getSubChild(candidate, c);
						showQuantity();
						int size = itemsRuleAll.size();

						// if Ct = ∅ then // if t does not contain any subset of candidate itemsets Ck,
						// mark the deleting tag
						if (size == 0) {
							c.setDeleteTag(true);
						} else {
							// for all candidates c∈Ct
							// c.count++;
							itemsRuleAll = GeneralUtil.pruneDuplicateItem(itemsRuleAll);
						}
					}
				}
			}

			int count = 0;

			dataResultItemsClone = cloneArray(dataResultItems);
			dataResultItems.clear();
			// make clone from result data
			for (I_Item i : itemsRuleAll) {
				double percent = 1.0 * i.getQuantity() / N;
				List<Word> subList;
				if (percent >= SUPPORT_MIN) {
					subList = i.getItemsChild();
					I_ComplexArray com = new I_ComplexArray(count, subList);
					dataResultItems.add(com);
					count++;
				}
			}

			// make clone from result data items if result change
			if (dataResultItems.size() > 0) {
				dataResultItemsClone = cloneArray(dataResultItems);
			}

			candidate = cloneArray(dataResultItems);

			if (dataResultItems.size() > 1) {
				show(dataResultItems);
				GeneralUtil.setTimeEnd();
				System.out.println("Support min = " + SUPPORT_MIN);
				System.out.println("Count : " + dataResultItems.size() + " items");
				System.out.println("----------------------------------------");
				System.out.println("----------------------------------------");
				System.out.println("----------------------------------------");
			}
		}
		System.out.println("Count : " + dataResultItemsClone.size() + " items");
		GeneralUtil.setTimeEnd();
		return dataResultItemsClone;
	}

	private void showQuantity() {
		System.out.println("======================");
		System.out.println(itemsRuleAll.size());
		for (I_Item i : itemsRuleAll) {
			for (Word w : i.getItemsChild()) {
				System.out.print(w.getWord() + " ");
			}
			System.out.println(" quantity = " + i.getQuantity());
		}

	}

	public void show(List<I_ComplexArray> result) {
		System.out.println("RESULT");
		System.out.println("+++++++++++++++++++++++++++");
		for (I_ComplexArray s : result) {
			for (Word i : s.getComplexObject()) {
				System.out.print(i.getWord() + " ");
			}
			System.out.println();
		}
	}

	private List<I_ComplexArray> cloneArray(List<I_ComplexArray> resultItems) {
		List<I_ComplexArray> dataResultItemsClone = new ArrayList<>();
		for (I_ComplexArray c : resultItems) {
			try {
				dataResultItemsClone.add(c.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return dataResultItemsClone;
	}

	private void getSubChild(List<I_ComplexArray> indicate, I_ComplexArray parent) {

		// for (I_ComplexArray parent : dataOriginalItems) {
		for (I_ComplexArray child : indicate) {

			I_Item i = new I_Item(parent.getComplexObject(), child.getComplexObject());
			if (!itemsRuleAll.contains(i)) {
				i.setItemsParent(parent.getComplexObject());
				itemsRuleAll.add(i);
			} else {
				I_Item clone = getItem(child.getComplexObject(), itemsRuleAll);
				if (null != clone) {
					clone.setItemsParent(parent.getComplexObject());
				}
			}
		}
		// }
	}

	private I_Item getItem(List<Word> child, List<I_Item> items) {
		for (I_Item i : items) {
			I_Item temp = new I_Item(child);
			if (i.equals(temp)) {
				return i;
			}
		}
		return null;
	}

	private List<I_ComplexArray> aprioriGen(List<I_ComplexArray> items) {
		List<I_ComplexArray> dataItemsChild = new ArrayList<>();
		List<Word> itemAtom = getAtomItems(items);

		System.out.println("items size = " + items.size());
		System.out.println("item atom size =" + itemAtom.size());

		for (I_ComplexArray s : items) {
			for (Word a : itemAtom) {

				List<Word> temp = new ArrayList<>();
				temp.addAll(s.getComplexObject());
				if (temp.contains(a)) {
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

	public List<I_ComplexArray> getAtomFirstData(List<I_ComplexArray> dataItemsParent) {
		List<I_ComplexArray> itemsFirst = new ArrayList<>();
		List<Word> atomItems = getAtomItems(dataItemsParent);
		I_ComplexArray com;
		List<Word> itemString;
		for (Word s : atomItems) {
			itemString = new ArrayList<>();
			itemString.add(s);
			com = new I_ComplexArray(itemString);
			itemsFirst.add(com);
		}
		return itemsFirst;
	}

	private List<Word> getAtomItems(List<I_ComplexArray> dataItemsParent) {
		List<Word> itemList = new ArrayList<>();
		for (I_ComplexArray com : dataItemsParent) {
			List<Word> itemValue = com.getComplexObject();
			for (Word s : itemValue) {
				if (!itemList.contains(s)) {
					itemList.add(s);
				}
			}
		}
		return itemList;
	}

	private List<I_ComplexArray> join(List<I_ComplexArray> cK1, List<I_ComplexArray> cK2) {
		List<I_ComplexArray> candidates = new ArrayList<>();

		int size1 = cK1.size();
		int size2 = cK2.size();
		List<Word> temp;
		for (int i = 0; i < size1; i++) {
			for (int j = i + 1; j < size2; j++) {
				temp = new ArrayList<>();
				temp.addAll(cK1.get(i).getComplexObject());
				List<Word> wordCk2 = cK2.get(j).getComplexObject();
				for (Word w : wordCk2) {
					if (!temp.contains(w)) {
						temp.add(w);
						break;
					}
				}
				I_ComplexArray complex = new I_ComplexArray(temp);
				candidates.add(complex);
			}
		}
		return candidates;
	}

}
