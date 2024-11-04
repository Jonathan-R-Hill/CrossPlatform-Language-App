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

import java.util.List;
import java.util.Random;

public class ReviewKnownWords   extends AppCompatActivity
{
    private Button confirmButton;
    private Button nextWordButton;
    private EditText userInput;
    private TextView wordToTranslateText;
    private TextView correctWordRevText;
    private Word currentWord;
    private List<Word> knownWords;
    private Random random;

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

        random = new Random(System.nanoTime());
        knownWords = WordStorage.loadKnownWords(this);
        if (knownWords.isEmpty()) {
            Toast.makeText(this, "No words to learn. Please add some words!", Toast.LENGTH_LONG).show();
        }
        else {
            loadRandomWord();
        }
        nextWordButton.setEnabled(false);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    protected void onResume() {
        super.onResume();
        random = new Random(System.nanoTime());
    }

    private void loadRandomWord() {
        if (knownWords.isEmpty()) {
            Toast.makeText(this, "No more words to learn!", Toast.LENGTH_LONG).show();
            return;
        }

        Word previousWord = currentWord;

        if (knownWords.size() > 1) {
            do {
                currentWord = knownWords.get(random.nextInt(knownWords.size()));
            } while (currentWord.equals(previousWord));
        } else {
            currentWord = knownWords.get(0);
        }

        wordToTranslateText.setText(currentWord.knownLang);
    }

    public void checkAnswer(View v)
    {
        String userAnswer = userInput.getText().toString().trim();

        // Check if answer is correct
        if (userAnswer.equalsIgnoreCase(currentWord.learnLang)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
            correctWordRevText.setText("Correct answer was:\n" + currentWord.learnLang);
        }

        // Disable confirm button and enable next button
        confirmButton.setEnabled(false);
        nextWordButton.setEnabled(true);
    }

    public void nextWord(View v)
    {
        userInput.setText("");
        correctWordRevText.setText("");

        loadRandomWord();

        confirmButton.setEnabled(true);
        nextWordButton.setEnabled(false);
    }
}
