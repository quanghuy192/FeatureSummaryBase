package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordOrientaionUtil {

	private FileReader readerPos, readerNeg;
	private BufferedReader bufferedReaderPos, bufferedReaderNeg;
	private final String FILE_POSITIVE_ADJ = "positive_wordnet.txt";
	private final String FILE_NEGATIVE_ADJ = "negative_wordnet.txt";

	private List<String> positiveAdjList;
	private List<String> negativeAdjList;

	public WordOrientaionUtil() {
		try {
			positiveAdjList = new ArrayList<>();
			negativeAdjList = new ArrayList<>();
			
			readerPos = new FileReader(new File(FILE_POSITIVE_ADJ));
			bufferedReaderPos = new BufferedReader(readerPos);
			readerNeg = new FileReader(new File(FILE_NEGATIVE_ADJ));
			bufferedReaderNeg = new BufferedReader(readerNeg);

			// Read adjective list
			readAjectiveFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readAjectiveFile() {
		String posW, negW;
		try {
			while (null != (posW = bufferedReaderPos.readLine())) {
				positiveAdjList.add(posW);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while (null != (negW = bufferedReaderNeg.readLine())) {
				negativeAdjList.add(negW);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
	}

	private void close() {
		try {
			bufferedReaderPos.close();
			readerPos.close();
			bufferedReaderNeg.close();
			readerNeg.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getPositiveAdjList() {
		return positiveAdjList;
	}

	public List<String> getNegativeAdjList() {
		return negativeAdjList;
	}

}
