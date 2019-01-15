package io;

import model.I_ComplexArray;
import model.Review;
import model.Sentences;
import model.Word;

import java.util.ArrayList;
import java.util.List;

public class FeatureBase {

    private List<String> nounTagList;
    private final String BLANK = " ";

    public FeatureBase() {
        nounTagList = new ArrayList<>();
        nounTagList.add("N");
        // nounTagList.add("Np");
        // nounTagList.add("Nc");
        // nounTagList.add("Nu");
        nounTagList.add("NP");
    }

    // Generate feature base raw file
    public List<I_ComplexArray> generateFeatureBase() {

        FileReviewWithPOS f = new FileReviewWithPOS();
        List<I_ComplexArray> featureBases = new ArrayList<>();
        List<Word> featureList;
        I_ComplexArray complexArray;

        List<Review> listReview = f.getReviewList();
        for (Review r : listReview) {

            featureList = new ArrayList<>();
            List<Sentences> sentences = r.getListSentences();
            for (Sentences s : sentences) {

                List<Word> words = s.getListWord();
                for (Word w : words) {
                    if (nounTagList.contains(w.getType()) && BLANK != w.getWord()) {
                        featureList.add(w);
                    }
                }
            }

            complexArray = new I_ComplexArray(featureList);
            featureBases.add(complexArray);
        }
        return featureBases;
    }

    public static void main(String[] args){
        FeatureBase f = new FeatureBase();
        List<I_ComplexArray> i_list = f.generateFeatureBase();
        for(I_ComplexArray i : i_list){
            System.out.print(i + "\n");
        }
    }
}
