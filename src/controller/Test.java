package controller;

import java.io.Serializable;
import java.util.List;

import model.Feature;
import model.I_ComplexArray;
import model.Word;
import old.I_AprioriAlgorithrm;
import utils.FeatureBaseUtils;
import utils.GeneralUtil;
import utils.WordOrientaionUtil;
import utils.WordUtils;

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
		// List<Word> l1 = new ArrayList<>();
		// l1.add(new Word("A"));
		// l1.add(new Word("C"));
		// l1.add(new Word("D"));
		// List<Word> l2 = new ArrayList<>();
		// l2.add(new Word("B"));
		// l2.add(new Word("C"));
		// l2.add(new Word("E"));
		// List<Word> l3 = new ArrayList<>();
		// l3.add(new Word("A"));
		// l3.add(new Word("B"));
		// l3.add(new Word("C"));
		// l3.add(new Word("E"));
		// List<Word> l4 = new ArrayList<>();
		// l4.add(new Word("B"));
		// l4.add(new Word("E"));
		//
		// dataItems.add(new I_ComplexArray(0, l1));
		// dataItems.add(new I_ComplexArray(1, l2));
		// dataItems.add(new I_ComplexArray(2, l3));
		// dataItems.add(new I_ComplexArray(3, l4));
		//
		// List<I_ComplexArray> result = new
		// AprioriOptimization().generate_K_ItemSet(dataItems);
		// for (I_ComplexArray s : result) {
		// for (Word i : s.getComplexObject()) {
		// System.out.print(i.getWord() + " ");
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
		/*
		 * HashMap<Integer, List<String[]>> rawData = new
		 * FeatureBaseUtils().featureBase(); HashMap<Integer, List<String[]>>
		 * featureBases = new AprioriAlgorithrm().generate_K_ItemSet(rawData); for
		 * (Map.Entry<Integer, List<String[]>> strings : featureBases.entrySet()) { for
		 * (String[] s : strings.getValue()) { for (String string : s) {
		 * System.out.print(string + " "); } System.out.println(); } }
		 */

		// String[] parent = {"A","B","C","A","D","G","B","C","E","F"};
		// String[] child = {"A","D","G","B"};
		// System.out.println(al.checkSubArrayContain(parent, child));

//		WordUtils utils = new WordUtils();
//		List<I_ComplexArray> complexArrays = utils.generateFeatureBase();
//		I_AprioriAlgorithrm algorithrm = new I_AprioriAlgorithrm();
//		GeneralUtil.setTimeStart();
//		List<I_ComplexArray> result = algorithrm.generate_K_ItemSet(complexArrays);
//		GeneralUtil.setTimeEnd();
//		// List<I_ComplexArray> result = algorithrm.generate_K_ItemSet(dataItems);
//		List<I_ComplexArray> items = algorithrm.getAtomFirstData(result);
//		for (I_ComplexArray s : items) {
//			for (Word i : s.getComplexObject()) {
//				System.out.print(i.getWord() + " ");
//			}
//			System.out.println();
//		}

		FeatureBaseUtils utilss = new FeatureBaseUtils();
		List<Feature> adjectiveList = utilss.getEffectiveWords();
		int count = 0;

		for (Feature f : adjectiveList) {
			count++;
			System.out.println(f.getFeature());
			System.out.println(f.getOpinionWords() + " ");
		}
		System.out.println(count);

		count = 0;
		WordOrientaionUtil u = new WordOrientaionUtil();
		List<String> l1 = u.getPositiveAdjList();
		List<String> l2 = u.getNegativeAdjList();
		for (String f : l1) {
			count++;
			System.out.println(f);
		}
		for (String f : l2) {
			count++;
			System.out.println(f);
		}
		System.out.println(count);

	}
}
