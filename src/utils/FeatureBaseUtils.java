package utils;

import java.util.ArrayList;
import java.util.List;

import controller.I_AprioriAlgorithrm;
import model.Feature;
import model.I_ComplexArray;
import model.Review;
import model.Sentences;
import model.Word;

public class FeatureBaseUtils {

	// private FileReader reader;
	// private BufferedReader bufferedReader;
	// private final String FEATURE_BASE_RAW_FILE = "feature_base_raw.txt";
	// private final String BLANK = " ";

	private List<I_ComplexArray> featureList;
	private List<Review> listReview;
	private WordUtils utils;
	private List<String> adjectiveTagList;
	private final String BLANK = " ";

	public FeatureBaseUtils() {
		// try {
		// reader = new FileReader(new File(FEATURE_BASE_RAW_FILE));
		// bufferedReader = new BufferedReader(reader);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		
		adjectiveTagList = new ArrayList<>();
		adjectiveTagList.add("A");
		adjectiveTagList.add("AP");

		utils = new WordUtils();
		listReview = utils.getReviewList();
	}

	// generate feature base after filter
	public List<I_ComplexArray> featureBase() {

		// HashMap<Integer, List<String[]>> featureBases = new HashMap<>();

		// read data include noun & noun phase
		// List<String[]> lineList = new ArrayList<String[]>();
		// String s;
		// int count = 0;
		// try {
		// while (null != (s = bufferedReader.readLine())) {
		// String[] wordList = s.split(BLANK);
		// lineList.add(wordList);
		// featureBases.put(count, lineList);
		// count++;
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// close();
		// }

		List<I_ComplexArray> featureBases = utils.generateFeatureBase();
		I_AprioriAlgorithrm algorithrm = new I_AprioriAlgorithrm();
		List<I_ComplexArray> result = algorithrm.generate_K_ItemSet(featureBases);
		featureList = algorithrm.getAtomFirstData(result);

		return featureList;
	}

	// private void close() {
	// try {
	// bufferedReader.close();
	// reader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public List<Feature> getOpinionWords() {
		List<Feature> opinionWords = new ArrayList<>();

		return opinionWords;
	}

	private List<Feature> getAdjectiveList() {
		List<Feature> adjectiveList = new ArrayList<>();

		if (GeneralUtil.isEmptyList(featureList)) {
			return adjectiveList;
		}

		if (GeneralUtil.isEmptyList(listReview)) {
			return adjectiveList;
		}

		String feature;
		Word word;
		for (I_ComplexArray com : featureList) {
			
			word = com.getComplexObject().get(0);
			if(null == word) {
				continue;
			}
			
			feature = word.getWord();
			for (Review review : listReview) {
				for (Sentences sen : review.getListSentences()) {
					if(sen.getOriginalSentences().contains(feature)) {
						Feature f = getFeature(sen);
						adjectiveList.add(f);
					}
				}
			}
		}

		return adjectiveList;
	}

	private Feature getFeature(Sentences sen) {
		Feature feature = new Feature();
		for (Word w : sen.getListWord()) {
			if(adjectiveTagList.contains(w.getType()) && BLANK != w.getWord()) {
				feature.getOpinionWords().add(w.getWord());
				feature.getOpinionSentences().add(sen.getOriginalSentences());
			}
		}
		return feature;
	}

}
