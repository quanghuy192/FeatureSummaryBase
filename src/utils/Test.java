package utils;

import java.io.Serializable;
import java.util.List;

import controller.AprioriAlgorithrm;
import model.Review;
import model.Sentences;
import model.Word;

public class Test implements Serializable {

	public static void main(String[] args) {
		WordUtils wordUtils = new WordUtils();
		List<Review> listReview = wordUtils.getReviewList();
		List<Sentences> sentences = listReview.get(0).getListSentences();
		List<Word> words = sentences.get(0).getListWord();

//		int i = 0;
//		while (i < words.size()) {
//			
//			Word word = words.get(i);
//			if (word.getType() == null && word.getWord() == null) {
//				i++;
//				System.out.println("count :" + i);
//			} else if (word.getType() != null) {
//				System.out.println(
//						word.getWord() + "==" + word.getType() + "==" + VnPOS_Utils.get_VN_POS_mean(word.getType()));
//				i++;
//				System.out.println("count :" + i);
//			} else {
//				Word newW = new Word();
//				Word w2 = words.get(i + 1);
//				newW.setWord(new StringBuilder(word.getWord()).append(" ").append(w2.getWord()).toString());
//				newW.setType(w2.getType());
//				i += 2;
//				System.out.println("count :" + i);
//				System.out.println(
//						newW.getWord() + "==" + newW.getType() + "==" + VnPOS_Utils.get_VN_POS_mean(newW.getType()));
//			}
//		}
		
		AprioriAlgorithrm al = new AprioriAlgorithrm();
		al.test();
	}
}
