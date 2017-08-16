package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import controller.Apriori_FindingSubChild_Thread.AprioriFindingSubChild;
import model.I_ComplexArray;
import model.I_Item;
import model.Review;
import model.Sentences;
import model.Word;
import utils.GeneralUtil;
import utils.WordUtils;

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
public class AprioriOptimization implements AprioriFindingSubChild {

	private String SEPERATOR = "";

	private List<I_ComplexArray> dataOriginalItems;
	private List<I_ComplexArray> dataResultItems;
	private int N;
	private double SUPPORT_MIN = 0.02;
	private volatile List<I_Item> itemsRuleAll;

	public AprioriOptimization() {
		dataResultItems = new ArrayList<>();
		itemsRuleAll = new ArrayList<>();
	}

	public List<I_ComplexArray> generate_K_ItemSet(List<I_ComplexArray> dataItemsParent) {

		dataOriginalItems = dataItemsParent;
		List<I_ComplexArray> dataResultItemsClone = new ArrayList<>();

		N = dataOriginalItems.size();

		// L1 = {large 1-itemsets};
		List<I_ComplexArray> candidate = getAtomFirstData(dataItemsParent);
		candidate = pruneRules(dataItemsParent);
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

			GeneralUtil.setTimeStart();
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

						for (int i = 0; i < Apriori_FindingSubChild_Thread.MULTI_THREAD; i++) {
							Apriori_FindingSubChild_Thread thread = new Apriori_FindingSubChild_Thread(this, i,
									candidate, c);
							thread.start();
							try {
								thread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						// getSubChild(candidate, c);
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
			GeneralUtil.setTimeEnd();
		}
		System.out.println("Count : " + dataResultItemsClone.size() + " items");
		
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

	// private List<I_ComplexArray> aprioriGen(List<I_ComplexArray> items) {
	// List<I_ComplexArray> dataItemsChild = new ArrayList<>();
	// List<Word> itemAtom = getAtomItems(items);
	//
	// System.out.println("items size = " + items.size());
	// System.out.println("item atom size =" + itemAtom.size());
	//
	// for (I_ComplexArray s : items) {
	// for (Word a : itemAtom) {
	//
	// List<Word> temp = new ArrayList<>();
	// temp.addAll(s.getComplexObject());
	// if (temp.contains(a)) {
	// continue;
	// }
	//
	// temp.add(a);
	// I_ComplexArray complex = new I_ComplexArray(temp);
	//
	// if (!dataItemsChild.contains(complex)) {
	// dataItemsChild.add(complex);
	// }
	// }
	// }
	// return dataItemsChild;
	// }

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
		
		System.out.println("========================================");
		System.out.println("Size Ck1 = " + size1);
		System.out.println("Size Ck2 = " + size2);
		
		int count = 0;
		
		List<Word> temp;
		for (int i = 0; i < size1; i++) {
			for (int j = i + 1; j < size2; j++) {
				count++;
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
		System.out.println("Real count = " + count);
		System.out.println("========================================");
		return candidates;
	}

	public List<I_ComplexArray> pruneRules(List<I_ComplexArray> list) {

		// if empty list
		if (null == list || list.size() == 0) {
			return Collections.emptyList();
		}

		// Get atom item form result list
		List<I_ComplexArray> result = getAtomFirstData(list);

		// prune feature base with one word
		result = oneWordPrunning(result);

		// This method checks features that contain
		// at least two words, which we call feature phrases, and remove
		// those that are likely to be meaningless.
		// result = compactnessPruning(result);

		// n this step, we focus on removing
		// redundant features that contain single words. To describe the
		// meaning of redundant features, we use the concept of p-support
		// (pure support). p-support of feature ftr is the number of sentences
		// that ftr appears in as a noun or noun phrase, and these sentences
		// must contain no feature phrase that is a superset of ftr.
		// result = redundancyPruning(result);

		return result;
	}

	private List<I_ComplexArray> oneWordPrunning(List<I_ComplexArray> list) {
		List<I_ComplexArray> result = new ArrayList<>();
		Word word;
		I_ComplexArray com;
		for (Iterator<I_ComplexArray> i = list.iterator(); i.hasNext();) {
			com = i.next();
			word = com.getComplexObject().get(0);
			String feature = word.getWord();
			if (feature.length() > 2) {
				result.add(com);
			}
		}
		return result;
	}

	public List<I_ComplexArray> compactnessPruning(List<I_ComplexArray> list) {

		List<I_ComplexArray> result = new ArrayList<>();
		I_ComplexArray com;
		// • Let f be a frequent feature phrase and f contains n
		// words. Assume that a sentence s contains f and the
		// sequence of the words in f that appear in s is: w1, w2,
		// …, wn. If the word distance in s between any two
		// adjacent words (wi and wi+1) in the above sequence is
		// no greater than 3, then we say f is compact in s.
		// • If f occurs in m sentences in the review database, and
		// it is compact in at least 2 of the m sentences, then we
		// call f a compact feature phrase.

		// Protect list from Concurrent Exception
		for (Iterator<I_ComplexArray> i = list.iterator(); i.hasNext();) {
			com = i.next();
			List<Word> temp = com.getComplexObject();
			Word w = temp.get(0);
			String word = w.getWord();

			String[] arrWord = word.split(SEPERATOR);

			// if word have only one word, it's compact feature base
			if (arrWord.length == 1) {
				result.add(com);
				// if word have more than 3 word, it's not compact feature base
			} else if (arrWord.length >= 3) {
				continue;
			} else {
				// check compactness rule
				if (compactnessRule(list)) {
					result.add(com);
				} else {
					continue;
				}
			}
		}
		return list;
	}

	private boolean compactnessRule(List<I_ComplexArray> list) {

		WordUtils utils = new WordUtils();
		List<Review> listReview = utils.getReviewList();
		Word word;
		I_ComplexArray com;

		for (Iterator<I_ComplexArray> i = list.iterator(); i.hasNext();) {
			com = i.next();
			word = com.getComplexObject().get(0);
			String[] items = word.getWord().split(SEPERATOR);
			if (items.length != 2) {
				return false;
			} else {
				String word1 = items[0];
				String word2 = items[1];
				int count = 0;

				for (Review r : listReview) {

					List<Sentences> sentences = r.getListSentences();
					for (Sentences s : sentences) {
						List<Word> words = s.getListWord();

						// If the word distance in s between any two
						// adjacent words (wi and wi+1) in the above sequence is
						// no greater than 3, then we say f is compact in s.
						for (int j = 0; j < words.size() - 2; j++) {
							String w = words.get(j).getWord();
							String w1 = words.get(j + 1).getWord();
							String w2 = words.get(j + 2).getWord();
							if ((word1.equalsIgnoreCase(w) && !word2.equalsIgnoreCase(w1))
									&& (word1.equalsIgnoreCase(w) && !word2.equalsIgnoreCase(w2))) {
								return false;
							} else {
								count++;
							}
						}
					}
				}
				if (count >= 2) {
					return true;
				}
			}
		}
		return true;
	}

	public List<I_ComplexArray> redundancyPruning(List<I_ComplexArray> list) {

		// We use the minimum p-support to prune those redundant features. If a
		// feature has a p-support lower than the minimum p-support (in our
		// system, we set it to 3) and the feature is a subset of another
		// feature phrase (which suggests that the feature alone may not be
		// interesting), it is pruned.
		return list;
	}

	@Override
	public void findSubChild(List<I_ComplexArray> indicate,
			I_ComplexArray parent) {
		List<I_Item> itemsRuleLocal = new ArrayList<>();
		for (I_ComplexArray child : indicate) {

			I_Item i = new I_Item(parent.getComplexObject(),
					child.getComplexObject());
			if (!itemsRuleLocal.contains(i)) {
				i.setItemsParent(parent.getComplexObject());
				itemsRuleLocal.add(i);
			} else {
				I_Item clone = getItem(child.getComplexObject(),
						itemsRuleLocal);
				if (null != clone) {
					clone.setItemsParent(parent.getComplexObject());
				}
			}
		}
		itemsRuleAll.addAll(itemsRuleLocal);
	}

}
