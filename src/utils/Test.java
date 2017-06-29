package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controller.I_AprioriAlgorithrm;
import model.ComplexArray;
import model.I_ComplexArray;

public class Test implements Serializable {

	public static void main(String[] args) {
		// WordUtils wordUtils = new WordUtils();
		// HashMap<Integer, List<String[]>> dataItems =
		// wordUtils.generateFeatureBase();
		// List<Review> listReview = wordUtils.getReviewList();
		// List<Sentences> sentences = listReview.get(0).getListSentences();
		// List<Word> words = sentences.get(0).getListWord();

		// int i = 0;
		// while (i < words.size()) {
		//
		// Word word = words.get(i);
		// if (word.getType() == null && word.getWord() == null) {
		// i++;
		// System.out.println("count :" + i);
		// } else if (word.getType() != null) {
		// System.out.println(
		// word.getWord() + "==" + word.getType() + "==" +
		// VnPOS_Utils.get_VN_POS_mean(word.getType()));
		// i++;
		// System.out.println("count :" + i);
		// } else {
		// Word newW = new Word();
		// Word w2 = words.get(i + 1);
		// newW.setWord(new StringBuilder(word.getWord()).append("
		// ").append(w2.getWord()).toString());
		// newW.setType(w2.getType());
		// i += 2;
		// System.out.println("count :" + i);
		// System.out.println(
		// newW.getWord() + "==" + newW.getType() + "==" +
		// VnPOS_Utils.get_VN_POS_mean(newW.getType()));
		// }
		// }

		// for (Word word : words) {
		// System.out.println(
		// word.getWord() + "==" + word.getType() + "==" +
		// VnPOS_Utils.get_VN_POS_mean(word.getType()));
		// }

		// String[][][] data = { { { "1" }, { "A", "C", "D" } },
		// { { "2" }, { "B", "C", "E" } },
		// { { "3" }, { "A", "B", "C", "E" } },
		// { { "4" }, { "B", "E" } } };
		// // HashMap<Integer, List<String[]>> dataItems = new HashMap<>();
		// List<I_ComplexArray> dataItems = new ArrayList<>();
		// List<String> l1 = new ArrayList<>();
		// l1.add("A");
		// l1.add("C");
		// l1.add("D");
		// List<String> l2 = new ArrayList<>();
		// l2.add("B");
		// l2.add("C");
		// l2.add("E");
		// List<String> l3 = new ArrayList<>();
		// l3.add("A");
		// l3.add("B");
		// l3.add("C");
		// l3.add("E");
		// List<String> l4 = new ArrayList<>();
		// l4.add("B");
		// l4.add("E");
		//
		// dataItems.add(new I_ComplexArray(0,l1));
		// dataItems.add(new I_ComplexArray(1,l2));
		// dataItems.add(new I_ComplexArray(2,l3));
		// dataItems.add(new I_ComplexArray(3,l4));
		//
		// List<I_ComplexArray> result = new
		// I_AprioriAlgorithrm().generate_K_ItemSet(dataItems);
		// for (I_ComplexArray s : result) {
		// for (String i : s.getComplexObject()) {
		// System.out.print(i + " ");
		// }
		// System.out.println();
		// }

		// for (int ii = 0; ii < data.length; ii++) {
		// String[][] items = data[ii];
		// datas = new ArrayList<>();
		// datas.add(items[1]);
		// dataItems.put(ii, datas);
		// }

		// AprioriAlgorithrm al = new AprioriAlgorithrm();
		// al.generate_K_ItemSet(dataItems);
		/*HashMap<Integer, List<String[]>> rawData = new FeatureBaseUtils().featureBase();
		HashMap<Integer, List<String[]>> featureBases = new AprioriAlgorithrm().generate_K_ItemSet(rawData);
		for (Map.Entry<Integer, List<String[]>> strings : featureBases.entrySet()) {
			for (String[] s : strings.getValue()) {
				for (String string : s) {
					System.out.print(string + " "); 
				}
				System.out.println();
			}
		}*/

		// String[] parent = {"A","B","C","A","D","G","B","C","E","F"};
		// String[] child = {"A","D","G","B"};
		// System.out.println(al.checkSubArrayContain(parent, child));
		
		WordUtils utils = new WordUtils();
		List<I_ComplexArray> complexArrays = utils.generateFeatureBase();
		I_AprioriAlgorithrm algorithrm = new I_AprioriAlgorithrm();
		List<I_ComplexArray> result = algorithrm.generate_K_ItemSet(complexArrays);
		List<I_ComplexArray> items = algorithrm.getAtomFirstData(result);
		for (I_ComplexArray s : items) {
			for (String i : s.getComplexObject()) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
}
