package com.example.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;
import java.util.List;

public class UpdateExistingWords extends AppCompatActivity
{

    private List<Word> learningWords;
    private WordsAdapter wordsAdapter;
    private RecyclerView recyclerView;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_learning_words);

        recyclerView = findViewById(R.id.wordsRecyclerView);
        updateButton = findViewById(R.id.updateButton);

        learningWords = WordStorage.loadLearningWords(this);

        // Set up RecyclerView
        wordsAdapter = new WordsAdapter(learningWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordsAdapter);

        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    public void updateWords(View v)
    {
        // Ensure all words are saved to avoid current field bug
        View currentFocus = this.getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }

        // Remove words with both fields empty or containing only whitespace
        learningWords.removeIf(word ->
                word.getKnownLang().trim().isEmpty() || word.getLearnLang().trim().isEmpty()
        );

        // Save the updated list to the file
        WordStorage.saveLearningWords(this, learningWords);
        Toast.makeText(this, "Words updated successfully!", Toast.LENGTH_LONG).show();
        updateButton.setEnabled(false);
        refreshPage();

        new Handler().postDelayed(() -> {
            updateButton.setEnabled(true);
            }, 3000);

    }

    private void refreshPage()
    {
        // Reload the updated data
        learningWords = WordStorage.loadLearningWords(this);
        wordsAdapter = new WordsAdapter(learningWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordsAdapter);
    }

}
