package utils;

import java.util.ArrayList;
import java.util.List;

import model.Feature;
import model.I_ComplexArray;
import model.Review;
import model.Sentences;
import model.Word;
import old.I_AprioriAlgorithrm;

public class FeatureBaseUtils {

	enum Orientation {
		POSITIVE, NEGATIVE
	}

	// private FileReader reader;
	// private BufferedReader bufferedReader;
	// private final String FEATURE_BASE_RAW_FILE = "feature_base_raw.txt";
	// private final String BLANK = " ";

	private List<I_ComplexArray> featureList;
	private List<Review> listReview;
	private WordUtils utils;
	private List<String> adjectiveTagList;

	private final String BLANK = "";
	private final String SEPERATOR = "-";

	private WordOrientaionUtil u = new WordOrientaionUtil();
	private List<String> posAdjList = u.getPositiveAdjList();
	private List<String> negAdjList = u.getNegativeAdjList();

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

		featureBase();
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

		for (I_ComplexArray s : featureList) {
			for (Word i : s.getComplexObject()) {
				System.out.print(i.getWord() + " ");
			}
			System.out.println();
		}

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

	public Orientation getOrientationSentences(Sentences sentences) {
		int orientation = 0;
		for (String string : adjectiveTagList) {
			
		}
		return Orientation.NEGATIVE;
	}

	public int getOrientationWord(Sentences sentences, Word word) {
		int orientation = 0;
		String opinionWord = word.getWord();
		if (posAdjList.contains(opinionWord)) {
			orientation = 1;
		}
		if (negAdjList.contains(opinionWord)) {
			orientation = -1;
		}
		return orientation;
	}

	public boolean isOpositeWord(Sentences sentences, Word word) {
		int orientation = 0;
		int positionWord = 0;
		for (int i = 0; i < sentences.getListWord().size(); i++) {
			
		}
		return false;
	}

	public List<Feature> getEffectiveWords() {
		List<Feature> effectiveWords = new ArrayList<>();
		List<Feature> featuresList = getAdjectiveList();

		if (GeneralUtil.isEmptyList(featuresList)) {
			return effectiveWords;
		}

		List<String> opinionAllList;
		String feature;
		List<Word> sentenceList;

		for (Feature f : featuresList) {
			opinionAllList = f.getOpinionWords();
			feature = f.getFeature();
			sentenceList = f.getOpinionSentences();

			int size = sentenceList.size();

			for (int i = 0; i < size; i++) {

				int featurePosition = -1;
				String word = BLANK;
				String item = BLANK;
				try {
					item = sentenceList.get(i).getWord();

					if (null == item) {
						continue;
					}

					word = item.split(SEPERATOR)[0];
				} catch (Exception e) {
					System.out.println(sentenceList.get(i).getWord());
				}
				if (word.equalsIgnoreCase(feature)) {
					featurePosition = i;
				}

				if (featurePosition == -1) {
					continue;
				}

				int adjPosition1 = featurePosition + 1;
				int adjPosition2 = featurePosition - 1;

				while (adjPosition1 < size && !opinionAllList.contains(sentenceList.get(adjPosition1).getWord()))
					adjPosition1++;
				while (adjPosition2 > -1 && !opinionAllList.contains(sentenceList.get(adjPosition2).getWord()))
					adjPosition2--;

				Feature resultFeature = new Feature();

				if (adjPosition1 < size) {
					String opinion1 = sentenceList.get(adjPosition1).getWord();
					if (opinionAllList.contains(opinion1)) {
						resultFeature.getOpinionWords().add(opinion1);
					}
				}

				if (adjPosition2 > -1) {
					String opinion2 = sentenceList.get(adjPosition2).getWord();
					if (opinionAllList.contains(opinion2)) {
						resultFeature.getOpinionWords().add(opinion2);
					}
				}

				resultFeature.setFeature(feature);
				effectiveWords.add(resultFeature);
			}
		}

		return effectiveWords;
	}

	public List<Feature> getAdjectiveList() {
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
			if (null == word) {
				continue;
			}

			feature = word.getWord();
			for (Review review : listReview) {
				for (Sentences sen : review.getListSentences()) {
					if (sen.getOriginalSentences().contains(feature)) {
						Feature f = getFeature(sen);
						f.setFeature(feature);

						if (!GeneralUtil.isEmptyList(f.getOpinionWords())) {
							adjectiveList.add(f);
						}
					}
				}
			}
		}

		return adjectiveList;
	}

	private Feature getFeature(Sentences sen) {
		Feature feature = new Feature();
		for (Word w : sen.getListWord()) {
			if (adjectiveTagList.contains(w.getType()) && BLANK != w.getWord()) {
				feature.getOpinionWords().add(w.getWord());
				feature.setOpinionSentences(sen.getListWord());
			}
		}
		return feature;
	}

}
