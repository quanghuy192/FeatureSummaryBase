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
		@SuppressWarnings("resource")
		BufferedWriter bufferedWriter = new BufferedWriter(writer);

		bufferedWriter.write("FINAL SUMMARY FOR IPHONE 6S !!! \n\n\n");
		bufferedWriter.write("Frequent feature");
		int count1, count2, count3;
		count1 = count2 = count3 = 0;

		List<OpinionResult> list1, list2, list3;
		list1 = new ArrayList<>();
		list2 = new ArrayList<>();
		list3 = new ArrayList<>();

		for (Review r : listReview) {
			List<Sentences> listS = r.getListSentences();
			for (int i = 0; i < listS.size(); i++) {
				Orientation o = utilss.getOrientationSentences(listS, i, feature);
				if (o.equals(Orientation.POSITIVE)) {
					list1.add(
							new OpinionResult(Orientation.POSITIVE, listS.get(i).getOriginalSentences().split("/")[0]));
					count1++;
				}
				if (o.equals(Orientation.NEGATIVE)) {
					list2.add(
							new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences().split("/")[0]));
					count2++;
				}
				if (o.equals(Orientation.NEUTRAL)) {
					list3.add(
							new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences().split("/")[0]));
					count3++;
				}
				System.out.println(o.name());
			}
		}
		bufferedWriter.write("Positive:" + count1);
		for (OpinionResult o1 : list1) {
			bufferedWriter.write(o1.toString());
			bufferedWriter.flush();
		}
		bufferedWriter.write("Negative:" + count2);
		for (OpinionResult o2 : list2) {
			bufferedWriter.write(o2.toString());
			bufferedWriter.flush();
		}
		bufferedWriter.write("Neutral:" + count3);
		for (OpinionResult o3 : list3) {
			bufferedWriter.write(o3.toString());
			bufferedWriter.flush();
		}
		System.out.println("================================");

		count1 = count2 = count3 = 0;
		list1.clear();
		list2.clear();
		list3.clear();

		for (Review r : listReview) {
			List<Sentences> listS = r.getListSentences();
			for (int i = 0; i < listS.size(); i++) {
				Orientation o = utilss.getOrientationSentences(listS, i, inFrequentFeature);
				if (o.equals(Orientation.POSITIVE)) {
					list1.add(
							new OpinionResult(Orientation.POSITIVE, listS.get(i).getOriginalSentences().split("/")[0]));
					count1++;
				}
				if (o.equals(Orientation.NEGATIVE)) {
					list2.add(
							new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences().split("/")[0]));
					count2++;
				}
				if (o.equals(Orientation.NEUTRAL)) {
					list3.add(
							new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences().split("/")[0]));
					count3++;
				}
				System.out.println(o.name());
			}
		}
		bufferedWriter.write("Positive:" + count1);
		for (OpinionResult o1 : list1) {
			bufferedWriter.write(o1.toString());
			bufferedWriter.flush();
		}
		bufferedWriter.write("Negative:" + count2);
		for (OpinionResult o2 : list2) {
			bufferedWriter.write(o2.toString());
			bufferedWriter.flush();
		}
		bufferedWriter.write("Neutral:" + count3);
		for (OpinionResult o3 : list3) {
			bufferedWriter.write(o3.toString());
			bufferedWriter.flush();
		}
		System.out.println("================================");
		bufferedWriter.close();

	}
}
