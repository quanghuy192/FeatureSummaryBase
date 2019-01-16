package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

public class Test{

    public static void main(String[] args) throws IOException {

        WordUtils wordUtils = new WordUtils();
        FeatureBaseUtils utilss = new FeatureBaseUtils();

        List<Review> listReview = wordUtils.getReviewList();
        List<I_ComplexArray> feature = utilss.getFeatureList();
        List<I_ComplexArray> inFrequentFeature = utilss.infrequentFeatureBase();

        wordUtils.closeReader();
        wordUtils.closeWrite();

        String FILE_OUTPUT_NAME = "final_summary.txt";
        FileWriter writer = new FileWriter(new File(FILE_OUTPUT_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("FINAL SUMMARY FOR IPHONE 6S !!! \n\n\n");
        bufferedWriter.write("Frequent feature \n".toUpperCase());

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
                        }
                        if (o.equals(Orientation.NEGATIVE)) {
                            list2.add(new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences(),
                                    featureString));
                        }
                        if (o.equals(Orientation.NEUTRAL)) {
                            list3.add(new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences(),
                                    featureString));
                        }
                    }
                }
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("====================================\n");
            bufferedWriter.write(com.getComplexObject().get(0).getWord().toUpperCase() + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.write("Positive:" + list1.size() + "\n\n");
            for (OpinionResult o1 : list1) {
                bufferedWriter.write("_ " + o1.toString() + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("Negative:" + list2.size() + "\n\n");
            for (OpinionResult o2 : list2) {
                bufferedWriter.write("_ " + o2.toString() + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("Neutral:" + list3.size() + "\n\n");
            for (OpinionResult o3 : list3) {
                bufferedWriter.write("_ " + o3.toString() + "\n");
                bufferedWriter.flush();
            }
        }

        bufferedWriter.write("\n");
        bufferedWriter.write("\n");

        bufferedWriter.write("Infrequent Frequent feature \n".toUpperCase());

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
                        }
                        if (o.equals(Orientation.NEGATIVE)) {
                            list2.add(new OpinionResult(Orientation.NEGATIVE, listS.get(i).getOriginalSentences(),
                                    featureString));
                        }
                        if (o.equals(Orientation.NEUTRAL)) {
                            list3.add(new OpinionResult(Orientation.NEUTRAL, listS.get(i).getOriginalSentences(),
                                    featureString));
                        }
                    }
                }
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("====================================\n");
            bufferedWriter.write(com.getComplexObject().get(0).getWord().toUpperCase() + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.write("Positive:" + list1.size() + "\n\n");
            for (OpinionResult o1 : list1) {
                bufferedWriter.write("_ " + o1.toString() + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("Negative:" + list2.size() + "\n\n");
            for (OpinionResult o2 : list2) {
                bufferedWriter.write("_ " + o2.toString() + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
            bufferedWriter.write("Neutral:" + list3.size() + "\n\n");
            for (OpinionResult o3 : list3) {
                bufferedWriter.write("_ " + o3.toString() + "\n");
                bufferedWriter.flush();
            }
        }
        bufferedWriter.close();
    }
}
