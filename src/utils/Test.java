package utils;

import java.io.Serializable;
import java.util.List;

import model.Review;

public class Test implements Serializable {

	public static void main(String[] args) {
		WordUtils wordUtils = new WordUtils();
		List<Review> listReview = wordUtils.getReviewList();
	}
}
