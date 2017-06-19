package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.AprioriAlgorithrm;

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
		// { { "4" }, { "B", "E" } }
		// };
		// HashMap<Integer, List<String[]>> dataItems = new HashMap<>();
		//
		// List<String[]> datas;
		// for (int ii = 0; ii < data.length; ii++) {
		// String[][] items = data[ii];
		// datas = new ArrayList<>();
		// datas.add(items[1]);
		// dataItems.put(ii, datas);
		// }

		// AprioriAlgorithrm al = new AprioriAlgorithrm();
		// al.generate_K_ItemSet(dataItems);
		HashMap<Integer, List<String[]>> rawData = new FeatureBaseUtils().featureBase();
		HashMap<Integer, List<String[]>> featureBases = new AprioriAlgorithrm().generate_K_ItemSet(rawData);
		for (Map.Entry<Integer, List<String[]>> strings : featureBases.entrySet()) {
			for (String[] s : strings.getValue()) {
				for (String string : s) {
					System.out.print(string + " ");
				}
				System.out.println();
			}
		}

		// String[] parent = {"A","B","C","A","D","G","B","C","E","F"};
		// String[] child = {"A","D","G","B"};
		// System.out.println(al.checkSubArrayContain(parent, child));

	}
}
