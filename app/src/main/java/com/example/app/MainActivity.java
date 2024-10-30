package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private List<Word> learningWords;
    private List<Word> knownWords;
    private Button practiceButton;
    private Button reviewKnownWordsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Check to see if words already exist and remove dupes
        learningWords = WordStorage.loadLearningWords(this);
        knownWords = WordStorage.loadKnownWords(this);
        WordStorage.removeKnownWordsFromLearning(this);
        WordStorage.removeDuplicates(this);

        practiceButton = findViewById(R.id.practiceButton);
        reviewKnownWordsButton = findViewById(R.id.reviewKnownWordsButton);

        if (learningWords.isEmpty()) { practiceButton.setEnabled(false); }
        if (knownWords.isEmpty()) { reviewKnownWordsButton.setEnabled(false); }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Reload word lists each time the activity is resumed
        learningWords = WordStorage.loadLearningWords(this);
        knownWords = WordStorage.loadKnownWords(this);

        // Enable or disable buttons based on the lists' content
        practiceButton.setEnabled(!learningWords.isEmpty());
        reviewKnownWordsButton.setEnabled(!knownWords.isEmpty());
    }

    public void loadQuizPage(View v) {
        Intent intent = new Intent(MainActivity.this, LearnVocabActivity.class);
        startActivity(intent);
    }

    public void loadCreateVocabPage(View v) {
        Intent intent = new Intent(MainActivity.this, CreateNewVocab.class);
        startActivity(intent);
    }

    public void loadCreateMultipleVocabPage(View v) {
        Intent intent = new Intent(MainActivity.this, CreateMultipleWords.class);
        startActivity(intent);
    }

    public void loadUpdateExistingWordsPage(View v) {
        Intent intent = new Intent(MainActivity.this, UpdateExistingWords.class);
        startActivity(intent);
    }

    public void loadReviewKnownWordsPage(View v) {
        Intent intent = new Intent(MainActivity.this, ReviewKnownWords.class);
        startActivity(intent);
    }

}
