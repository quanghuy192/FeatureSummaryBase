package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;
import model.OpinionResult;
import model.Review;
import model.Sentences;
import model.Word;
import utils.FeatureBaseUtils;
import utils.FeatureBaseUtils.Orientation;
import utils.WordUtils;

public class Test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889473193102076069L;

	public static void main(String[] args) throws IOException {

		WordUtils wordUtils = new WordUtils();
		FeatureBaseUtils utilss = new FeatureBaseUtils();

		List<Review> listReview = wordUtils.getReviewList();
		List<I_ComplexArray> feature = utilss.featureBase();
		List<I_ComplexArray> inFrequentFeature = utilss.infrequentFeatureBase();

		String FILE_OUTPUT_NAME = "final_summary.txt";
		FileWriter writer = new FileWriter(new File(FILE_OUTPUT_NAME));
		BufferedWriter bufferedWriter = new BufferedWriter(writer);

		bufferedWriter.write("FINAL SUMMARY FOR IPHONE 6S !!! \n\n\n");
		bufferedWriter.write("Frequent feature \n".toUpperCase());

		int count1, count2, count3;
		count1 = count2 = count3 = 0;

		List<OpinionResult> list1, list2, list3;
		list1 = new ArrayList<>();
		list2 = new ArrayList<>();
		list3 = new ArrayList<>();

		for (I_ComplexArray com : feature) {
			List<Word> listW = com.getComplexObject();
			Word w = listW.get(0);
			String featureString = w.getWord();

			list1.clear();
			list2.clear();
			list3.clear();
			for (Review r : listReview) {
				List<Sentences> listS = r.getListSentences();
				for (int i = 0; i < listS.size(); i++) {
					if (listS.get(i).getOriginalSentences().toLowerCase().contains(featureString)) {
						Orientation o = utilss.getOrientationSentences(listS, i, feature);
						if (o.equals(Orientation.POSITIVE)) {
							list1.add(new OpinionResult(Orientation.POSITIVE, listS.get(i).getOriginalSentences(),
									featureString));
							count1++;
						}
						if (o.equals(Orientation.NEGATIVE)) {
							list2.add(new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences(),
									featureString));
							count2++;
						}
						if (o.equals(Orientation.NEUTRAL)) {
							list3.add(new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences(),
									featureString));
							count3++;
						}
					}
				}
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("====================================\n");
			bufferedWriter.write(com.getComplexObject().get(0).getWord().toUpperCase() + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write("Positive:" + count1 + "\n\n");
			for (OpinionResult o1 : list1) {
				bufferedWriter.write("_ " + o1.toString() + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("Negative:" + count2 + "\n\n");
			for (OpinionResult o2 : list2) {
				bufferedWriter.write("_ " + o2.toString() + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("Neutral:" + count3 + "\n\n");
			for (OpinionResult o3 : list3) {
				bufferedWriter.write("_ " + o3.toString() + "\n");
				bufferedWriter.flush();
			}
		}

		bufferedWriter.write("\n");
		bufferedWriter.write("\n");

		bufferedWriter.write("Infrequent Frequent feature \n".toUpperCase());

		count1 = count2 = count3 = 0;

		for (I_ComplexArray com : inFrequentFeature) {
			List<Word> listW = com.getComplexObject();
			Word w = listW.get(0);
			String featureString = w.getWord();

			list1.clear();
			list2.clear();
			list3.clear();
			for (Review r : listReview) {
				List<Sentences> listS = r.getListSentences();
				for (int i = 0; i < listS.size(); i++) {
					if (listS.get(i).getOriginalSentences().toLowerCase().contains(featureString)) {
						Orientation o = utilss.getOrientationSentences(listS, i, inFrequentFeature);
						if (o.equals(Orientation.POSITIVE)) {
							list1.add(new OpinionResult(Orientation.POSITIVE, listS.get(i).getOriginalSentences(),
									featureString));
							count1++;
						}
						if (o.equals(Orientation.NEGATIVE)) {
							list2.add(new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences(),
									featureString));
							count2++;
						}
						if (o.equals(Orientation.NEUTRAL)) {
							list3.add(new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences(),
									featureString));
							count3++;
						}
					}
				}
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("====================================\n");
			bufferedWriter.write(com.getComplexObject().get(0).getWord().toUpperCase() + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write("Positive:" + count1 + "\n\n");
			for (OpinionResult o1 : list1) {
				bufferedWriter.write("_ " + o1.toString() + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("Negative:" + count2 + "\n\n");
			for (OpinionResult o2 : list2) {
				bufferedWriter.write("_ " + o2.toString() + "\n");
				bufferedWriter.flush();
			}
			bufferedWriter.write("\n");
			bufferedWriter.write("Neutral:" + count3 + "\n\n");
			for (OpinionResult o3 : list3) {
				bufferedWriter.write("_ " + o3.toString() + "\n");
				bufferedWriter.flush();
			}
		}

	}
}
