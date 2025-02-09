package com.example.app;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleWordsQuiz extends AppCompatActivity
{
    private Button[] nativeButtons;
    private Button[] targetButtons;

    private List<Word> learningWords;
    private Map<String, String> wordPairs = new HashMap<>();
    private String selectedWord = null; // Stores the first selected word (can be either language)
    private Button selectedButton = null; // Stores the currently highlighted button
    private boolean isNativeSelected = false; // Tracks if the selected word is from native buttons
    private int remainingMatches = 4; // Tracks how many matches are left

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice_activity);

        nativeButtons = new Button[]{
                findViewById(R.id.nativeWord1),
                findViewById(R.id.nativeWord2),
                findViewById(R.id.nativeWord3),
                findViewById(R.id.nativeWord4)
        };

        targetButtons = new Button[]{
                findViewById(R.id.targetWord1),
                findViewById(R.id.targetWord2),
                findViewById(R.id.targetWord3),
                findViewById(R.id.targetWord4)
        };

        // Load words
        learningWords = WordStorage.loadLearningWords(this);
        if (learningWords.size() < 4) {
            Toast.makeText(this, "Please add at least 4 words.", Toast.LENGTH_LONG).show();
            return;
        }

        loadNewWords();
    }

    private void loadNewWords()
    {
        wordPairs.clear();
        remainingMatches = 4;
        selectedWord = null;
        selectedButton = null;
        isNativeSelected = false;

        // Enable buttons and reset colors
        for (Button btn : targetButtons) {
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.RED);
            btn.setTextSize(16);
        }

        for (Button btn : nativeButtons) {
            btn.setEnabled(true);
            btn.setBackgroundColor(Color.RED);
            btn.setTextSize(16);
        }

        // Shuffle words
        Collections.shuffle(learningWords);

        // Pick first 4 words and store pairs
        for (int i = 0; i < 4; i++) {
            wordPairs.put(learningWords.get(i).getKnownLang(), learningWords.get(i).getLearnLang());
        }

        List<String> nativeWords = new ArrayList<>(wordPairs.keySet());
        List<String> targetWords = new ArrayList<>(wordPairs.values());

        // Shuffle for random button assignment
        Collections.shuffle(nativeWords);
        Collections.shuffle(targetWords);

        // Assign words to buttons
        for (int i = 0; i < 4; i++)
        {
            nativeButtons[i].setText(nativeWords.get(i));
            targetButtons[i].setText(targetWords.get(i));

            final String nativeWord = nativeWords.get(i);
            final String targetWord = targetWords.get(i);

            int finalI = i;
            nativeButtons[i].setOnClickListener(v -> onWordSelected(nativeWord, nativeButtons[finalI], true));
            targetButtons[i].setOnClickListener(v -> onWordSelected(targetWord, targetButtons[finalI], false));
        }
    }

    private void onWordSelected(String word, Button button, boolean isNative)
    {
        if (selectedWord == null)
        {
            // First word selected
            selectedWord = word;
            selectedButton = button;
            isNativeSelected = isNative;

            // Highlight selected button
            button.setBackgroundColor(Color.BLUE);
        }
        else {
            // Prevent selecting from the same column (native or target)
            if ((isNativeSelected && isNative) || (!isNativeSelected && !isNative))
            {
                // Update selection instead of matching
                selectedWord = word;
                selectedButton.setBackgroundColor(Color.RED);
                selectedButton = button;
                button.setBackgroundColor(Color.BLUE);
            } else
            {
                checkMatch(selectedWord, word, selectedButton, button);
                selectedWord = null;
                selectedButton = null;
            }
        }
    }

    private void checkMatch(String firstWord, String secondWord, Button firstButton, Button secondButton) {
        boolean isCorrectMatch = wordPairs.containsKey(firstWord) &&
                wordPairs.get(firstWord).equals(secondWord)
                || wordPairs.containsKey(secondWord) && wordPairs.get(secondWord).equals(firstWord);

        if (isCorrectMatch) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();

            // Disable matched buttons
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
            firstButton.setBackgroundColor(Color.GRAY);
            secondButton.setBackgroundColor(Color.GRAY);

            // Remove matched words
            remainingMatches--;

            if (remainingMatches == 0) {
                if (learningWords.size() < 4) {
                    Toast.makeText(this, "No more words left!", Toast.LENGTH_SHORT).show();
                } else {
                    loadNewWords();
                }
            }
        } else {
            Toast.makeText(this, "Wrong pair, try again!", Toast.LENGTH_SHORT).show();
            firstButton.setBackgroundColor(Color.RED);
            selectedButton.setBackgroundColor(Color.RED);
        }
    }
}
