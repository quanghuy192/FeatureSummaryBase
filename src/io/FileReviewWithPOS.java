package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Review;
import utils.GeneralUtil;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.hus.nlp.utils.UTF8FileUtility;

public class FileReviewWithPOS {

    private FileReader reader;
    private FileWriter writer;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private List<String> getReviewFromFileInput(String fileName) {
        List<String> wordList = new ArrayList<>();
        String[] sentences = UTF8FileUtility.getLines(fileName);

        VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
        for (String s : sentences) {
            try {
                String item = tagger.tagText(s);
                if (item.length() > 0) {
                    wordList.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return wordList;
    }

    private void writeReviewWithPOS(List<String> wordsList) {
        try {
            writer = new FileWriter(new File("words_with_tags.txt"));
            bufferedWriter = new BufferedWriter(writer);
            for (String words : wordsList) {
                bufferedWriter.write(words);
                bufferedWriter.write("\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<String> in;

    private synchronized void generateWordTagData() {
        Thread t = new Thread(() -> in = getReviewFromFileInput("words_with_tags.txt"));
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (GeneralUtil.isEmptyList(in)) {
            in = new ArrayList<>();
        }
        writeReviewWithPOS(in);
    }

    public void closeReader() {
        try {
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWrite() {
        try {
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewList() {

        // Create data
        generateWordTagData();

        // Open data
        openWordTagData();

        List<Review> reviewList = new ArrayList<>();
        String s;
        try {
            while (null != (s = bufferedReader.readLine())) {
                Review review = new Review();
                review.setOriginalReview(s);
                reviewList.add(review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    private void openWordTagData() {
        try {
            reader = new FileReader(new File("words_with_tags.txt"));
            bufferedReader = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

