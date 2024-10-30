package com.example.app.Data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordStorage {
    private static final String LEARNING_FILE_NAME = "learningWords.json";
    private static final String KNOWN_FILE_NAME = "knownWords.json";
    private static final Gson gson = new Gson();

    // Save a list of words to a specified file
    private static void saveWordsToFile(Context context, String fileName, List<Word> words) {
        String json = gson.toJson(words);

        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load words from a specified file
    private static List<Word> loadWordsFromFile(Context context, String fileName) {
        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader reader = new InputStreamReader(fis)) {

            return gson.fromJson(reader, new TypeToken<List<Word>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if there’s an error
        }
    }

    // Save words to learning list
    public static void saveLearningWords(Context context, List<Word> words) {
        saveWordsToFile(context, LEARNING_FILE_NAME, words);
    }

    // Save words to known list
    private static void saveKnownWords(Context context, List<Word> words) {
        saveWordsToFile(context, KNOWN_FILE_NAME, words);
    }

    // Load learning words
    public static List<Word> loadLearningWords(Context context) {
        return loadWordsFromFile(context, LEARNING_FILE_NAME);
    }

    // Load known words
    public static List<Word> loadKnownWords(Context context) {
        return loadWordsFromFile(context, KNOWN_FILE_NAME);
    }

    // Add a single word to the learning list and save it
    public static void addWordToLearning(Context context, Word word) {
        List<Word> learningWords = loadLearningWords(context);
        learningWords.add(word);
        saveLearningWords(context, learningWords);
    }

    public static void addMultipleWordsToLearning(Context context, List<Word> newWords) {
        // Load the existing learning words list from JSON
        List<Word> learningWords = loadLearningWords(context);
        learningWords.addAll(newWords);

        saveLearningWords(context, learningWords);
    }


    // Add a single word to the known list and save it
    public static void addWordToKnown(Context context, Word word) {
        List<Word> knownWords = loadKnownWords(context);
        knownWords.add(word);
        saveKnownWords(context, knownWords);
    }

    // Move a word from learning list to known list
    public static void moveWordToKnown(Context context, Word word) {
        List<Word> learningWords = loadLearningWords(context);
        List<Word> knownWords = loadKnownWords(context);

        // Check if the word exists in the learning list & update lists
        if (learningWords.remove(word)) {
            knownWords.add(word);
            saveLearningWords(context, learningWords);
            saveKnownWords(context, knownWords);
        }
    }

    public static void removeKnownWordsFromLearning(Context context) {
        List<Word> learningWords = loadLearningWords(context);
        List<Word> knownWords = loadKnownWords(context);

        List<String> seenKnownWord = new ArrayList<>();

        Iterator<Word> iterator = knownWords.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (seenKnownWord.contains(word.learnLang)) {
                iterator.remove(); // Remove duplicate known words
            } else {
                seenKnownWord.add(word.learnLang);
            }
        }

        iterator = learningWords.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (seenKnownWord.contains(word.learnLang)) {
                iterator.remove(); // Remove words in learning list that are also in known words
            }
        }

        // Save the updated learning list
        saveLearningWords(context, learningWords);
    }


    private static List<Word> removeDuplicate(List<Word> list) {
        List<String> seenLearnLang = new ArrayList<>();

        Iterator<Word> iterator = list.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (seenLearnLang.contains(word.learnLang)) {
                iterator.remove();
            } else {
                seenLearnLang.add(word.learnLang);
            }
        }

        return list;
    }

    public static void removeDuplicates(Context context) {
        List<Word> learningWords = loadLearningWords(context);
        List<Word> knownWords = loadKnownWords(context);

        learningWords = removeDuplicate(learningWords);
        knownWords = removeDuplicate(knownWords);

        saveLearningWords(context, learningWords);
        saveKnownWords(context, knownWords);
    }

}
