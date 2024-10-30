package com.example.app;

import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import java.util.List;
import java.util.Random;

public class LearnVocabActivity extends AppCompatActivity
{
    private Button confirmButton;
    private Button nextWordButton;
    private Button addToKnownWordsButton;
    private EditText userInput;
    private TextView wordToTranslateText;
    private TextView correctWordText;
    private Word currentWord;
    private List<Word> learningWords;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_vocab_activity);

        confirmButton = findViewById(R.id.confirmButton);
        nextWordButton = findViewById(R.id.nextWordButton);
        userInput = findViewById(R.id.userInput);
        wordToTranslateText = findViewById(R.id.wordToTranslateText);
        addToKnownWordsButton = findViewById(R.id.addToKnownButton);
        correctWordText = findViewById(R.id.correctWordText);

        // Initialize random and load learning words from file
        random = new Random();
        learningWords = WordStorage.loadLearningWords(this);

        if (learningWords.isEmpty()) {
            Toast.makeText(this, "No words to learn. Please add some words!", Toast.LENGTH_LONG).show();
        }
        else {
            loadRandomWord();
        }
        nextWordButton.setEnabled(false);
        addToKnownWordsButton.setEnabled(false);

        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    public void checkAnswer(View v)
    {
        String userAnswer = userInput.getText().toString().trim();

        // Check if answer is correct
        if (userAnswer.equalsIgnoreCase(currentWord.learnLang)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
            addToKnownWordsButton.setEnabled(true);
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
            correctWordText.setText("Correct answer was:\n" + currentWord.learnLang);
        }

        // Disable confirm button and enable next button
        confirmButton.setEnabled(false);
        nextWordButton.setEnabled(true);
    }

    public void nextWord(View v)
    {
        userInput.setText("");
        correctWordText.setText("");
        loadRandomWord();

        confirmButton.setEnabled(true);
        nextWordButton.setEnabled(false);
        addToKnownWordsButton.setEnabled(false);
    }

    public void addToKnownWords(View v)
    {
        Button b = (Button) v;

        if (currentWord != null) {
            WordStorage.addWordToKnown(this, currentWord);
            learningWords.remove(currentWord);
            WordStorage.saveLearningWords(this, learningWords);
            Toast.makeText(this, "Word added to known words!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "No word to add.", Toast.LENGTH_LONG).show();
        }
        addToKnownWordsButton.setEnabled(false);
    }


    private void loadRandomWord()
    {
        Word current = currentWord;
        if (learningWords.size() > 1) {
            while (current == currentWord) {
                if (!learningWords.isEmpty()) {
                    // Pick a random word from the learning list
                    currentWord = learningWords.get(random.nextInt(learningWords.size()));
                    wordToTranslateText.setText(currentWord.knownLang); // Display the known language word
                } else {
                    Toast.makeText(this, "No more words to learn!", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            if (!learningWords.isEmpty()) {
                currentWord = learningWords.get(random.nextInt(learningWords.size()));
                wordToTranslateText.setText(currentWord.knownLang);
            } else {
                Toast.makeText(this, "No more words to learn!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
