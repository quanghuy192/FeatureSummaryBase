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

	public static enum Orientation {
		POSITIVE, NEGATIVE, NEUTRAL
	}

	private List<I_ComplexArray> featureList, infrequentFeatureList;
	private List<Review> listReview;
	private WordUtils utils;
	private List<String> adjectiveTagList;
	private List<String> nounTagList;

	private final String BLANK = "";
	private final String SEPERATOR = "-";

	private WordOrientaionUtil u = new WordOrientaionUtil();
	private List<String> posAdjList = u.getPositiveAdjList();
	private List<String> negAdjList = u.getNegativeAdjList();

	public FeatureBaseUtils() {

		adjectiveTagList = new ArrayList<>();
		adjectiveTagList.add("A");
		adjectiveTagList.add("AP");

		nounTagList = new ArrayList<>();
		nounTagList.add("N");
		// nounTagList.add("Np");
		// nounTagList.add("Nc");
		// nounTagList.add("Nu");
		// nounTagList.add("NP");

		utils = new WordUtils();
		listReview = utils.getReviewList();

		featureBase();
	}

	// generate feature base after filter
	public List<I_ComplexArray> featureBase() {

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

	// generate ỉnfrequent feature base after filter
	public List<I_ComplexArray> infrequentFeatureBase() {

		List<String> infrequentFeature = getInfrequentFeature();
		infrequentFeatureList = new ArrayList<>();
		I_ComplexArray com = null;

		for (String s : infrequentFeature) {
			List<Word> listW = new ArrayList<>();
			Word w = new Word();
			w.setOriginalWord(s);
			w.setType("N");
			w.setWord(s);
			listW.add(w);

			com = new I_ComplexArray();
			com.setComplexObject(listW);
			infrequentFeatureList.add(com);
		}

		return infrequentFeatureList;
	}

	public Orientation getOrientationSentences(List<Sentences> listS, int position, List<I_ComplexArray> features) {
		Sentences sentences = listS.get(position);
		int orientation = 0;
		for (I_ComplexArray ic : features) {
			for (Word w : ic.getComplexObject()) {
				List<Word> lw = sentences.getListWord();
				if (!lw.contains(w)) {
					continue;
				}
				for (Word word : lw) {
					if (adjectiveTagList.contains(word.getType())) {
						if (posAdjList.contains(word.getWord())) {
							orientation += getOrientationWord(sentences, word.getWord());
						}
					}
				}
				if (orientation > 0)
					return Orientation.POSITIVE;
				else if (orientation < 0)
					return Orientation.NEGATIVE;
				else {
					for (Feature f : getAdjectiveList()) {
						if (f.getFeature().equalsIgnoreCase(w.getWord())) {
							Feature effectiveWord = exactlyEffectiveWords(f);

							for (String s : effectiveWord.getOpinionWords()) {
								if (posAdjList.contains(s)) {
									orientation += getOrientationWord(sentences, s);
								}
							}

							if (orientation > 0)
								return Orientation.POSITIVE;
							else if (orientation < 0)
								return Orientation.NEGATIVE;
							else {
								int size = listS.size();
								if (position > 0 && position < size) {
									return getOrientationSentences(listS, position - 1, features);
								} else {
									return Orientation.NEUTRAL;
								}
								// return Orientation.NEUTRAL;
							}
						}
					}
				}
			}
		}
		return Orientation.NEGATIVE;
	}

	public int getOrientationWord(Sentences sentences, String word) {

		WordOrientaionUtil utils = new WordOrientaionUtil();
		List<String> positiveWordList = utils.getOppositeWordList();

		int orientation = 0;
		int positionWord = -1;

		int size = sentences.getListWord().size();
		List<Word> lw = sentences.getListWord();

		if (posAdjList.contains(word)) {
			orientation = 1;
		}
		if (negAdjList.contains(word)) {
			orientation = -1;
		}

		for (int i = 0; i < size; i++) {
			if (word.equalsIgnoreCase(lw.get(i).getWord())) {
				positionWord = i;
			}
		}
		if (positionWord == -1) {
			return 0;
		}

		int adjPosition1 = positionWord + 1;
		int adjPosition2 = positionWord - 1;

		int count = 0;
		while (adjPosition1 < size && count < 3) {
			String w1 = lw.get(adjPosition1).getWord();
			if (positiveWordList.contains(w1)) {
				orientation *= (-1);
			}
			adjPosition1++;
			count++;
		}

		count = 0;
		while (adjPosition2 > -1 && count < 3) {
			String w2 = lw.get(adjPosition2).getWord();
			if (positiveWordList.contains(w2)) {
				orientation *= (-1);
			}
			adjPosition2--;
			count++;
		}

		return orientation;
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

	public Feature exactlyEffectiveWords(Feature feature) {

		Feature resultFeature = new Feature();

		if (GeneralUtil.isEmpty(feature.getFeature())) {
			return resultFeature;
		}

		List<String> opinionAllList;
		List<Word> sentenceList;

		opinionAllList = feature.getOpinionWords();
		sentenceList = feature.getOpinionSentences();

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
			if (word.equalsIgnoreCase(feature.getFeature())) {
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

			resultFeature.setFeature(feature.getFeature());
		}

		return resultFeature;
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

	public List<String> getInfrequentFeature() {

		boolean escape = false;

		List<String> infrequentFeature = new ArrayList<>();
		for (Review re : listReview) {
			for (Sentences sen : re.getListSentences()) {
				escape = false;
				for (I_ComplexArray com : featureList) {
					Word word = com.getComplexObject().get(0);
					if (sen.getOriginalSentences().toLowerCase().contains(word.getWord())) {
						escape = true;
					}
				}

				if (escape) {
					break;
				}

				List<String> opinionWordList = new ArrayList<>();
				opinionWordList.addAll(posAdjList);
				opinionWordList.addAll(negAdjList);
				for (String o : opinionWordList) {
					List<Word> listW = sen.getListWord();
					for (Word word : listW) {
						String w = word.getWord();
						String t = word.getType();

						if (GeneralUtil.isEmpty(w) || GeneralUtil.isEmpty(t)) {
							continue;
						}

						if (w.contains(o) && adjectiveTagList.contains(t)) {
							List<String> opinionWords = findInfrequentFeature(o, listW);
							infrequentFeature.addAll(opinionWords);
						}
					}

				}
			}
		}
		infrequentFeature = GeneralUtil.pruneDuplicateString(infrequentFeature);
		return infrequentFeature;
	}

	private List<String> findInfrequentFeature(String o, List<Word> listW) {
		List<String> results = new ArrayList<>();
		int positionW = -1;
		int size = listW.size();
		for (int i = 0; i < size; i++) {
			String w = listW.get(i).getWord();

			if (GeneralUtil.isEmpty(w)) {
				continue;
			}

			if (w.contains(o)) {
				positionW = i;
			}
		}
		if (positionW == -1) {
			return results;
		}

		int optPosition1 = positionW + 1;
		int optPosition2 = positionW - 1;

		int count = 0;
		while (optPosition1 < size && count < 3) {
			String w1 = listW.get(optPosition1).getWord();
			String t1 = listW.get(optPosition1).getType();
			if (nounTagList.contains(t1)) {
				if (w1.length() > 2) {
					results.add(w1);
				}
				break;
			}
			optPosition1++;
			count++;
		}

		count = 0;
		while (optPosition2 > -1 && count < 3) {
			String w2 = listW.get(optPosition2).getWord();
			String t2 = listW.get(optPosition2).getType();
			if (nounTagList.contains(t2)) {
				if (w2.length() > 2) {
					results.add(w2);
				}
				break;
			}
			optPosition2--;
			count++;
		}
		return results;
	}

}
