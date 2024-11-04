package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReviewKnownWords extends AppCompatActivity
{
    private Button confirmButton;
    private Button nextWordButton;
    private EditText userInput;
    private TextView wordToTranslateText;
    private TextView correctWordRevText;
    private Word currentWord;
    private List<Word> knownWords;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_vocab_activity);

        confirmButton = findViewById(R.id.confirmButton);
        nextWordButton = findViewById(R.id.nextWordButton);
        userInput = findViewById(R.id.userInput);
        wordToTranslateText = findViewById(R.id.wordToTranslateText);
        correctWordRevText = findViewById(R.id.correctWordRevText);

        knownWords = WordStorage.loadKnownWords(this);

        if (knownWords.isEmpty()) {
            Toast.makeText(this, "No words to review. Please add some words!", Toast.LENGTH_LONG).show();
        }
        else {
            shuffleWords();
            loadNextWord();
        }

        nextWordButton.setEnabled(false);

        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    private void loadNextWord() {
        if (knownWords.isEmpty()) {
            Toast.makeText(this, "No more words to review!", Toast.LENGTH_LONG).show();
            return;
        }

        currentWord = knownWords.get(currentIndex);
        wordToTranslateText.setText(currentWord.knownLang);

        currentIndex++;

        if (currentIndex >= knownWords.size()) {
            currentIndex = 0;
            shuffleWords();
        }
    }

    private void shuffleWords() {
        Collections.shuffle(knownWords, new Random(System.nanoTime()));
    }

    public void checkAnswer(View v)
    {
        String userAnswer = userInput.getText().toString().trim();

        if (userAnswer.equalsIgnoreCase(currentWord.learnLang)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
            correctWordRevText.setText("Correct answer was:\n" + currentWord.learnLang);
        }

        confirmButton.setEnabled(false);
        nextWordButton.setEnabled(true);
    }

    public void nextWord(View v)
    {
        userInput.setText("");
        correctWordRevText.setText("");

        loadNextWord();

        confirmButton.setEnabled(true);
        nextWordButton.setEnabled(false);
    }
}
