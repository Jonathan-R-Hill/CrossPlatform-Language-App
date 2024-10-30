package com.example.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;

public class CreateNewVocab extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_word);

        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    public void createNewWord(View v) {
        EditText knownInput = findViewById(R.id.knownLangUserInput);
        EditText targetInput = findViewById(R.id.targetLangUserInput);

        String nativeWord = knownInput.getText().toString().trim();
        String targetWord = targetInput.getText().toString().trim();
        Button b = (Button) v;

        if (nativeWord.isEmpty() || targetWord.isEmpty()) {
            b.setText("Error: check your fields!");
            b.setEnabled(false);
        }
        else {
            Word newWord = new Word(nativeWord, targetWord);
            WordStorage.addWordToLearning(this, newWord);  // 'this' is used to pass the current context

            knownInput.setText("");
            targetInput.setText("");

            b.setText("Word added!");
            b.setEnabled(false);
        }
        // Use a Handler to create a delay
        new Handler().postDelayed(() -> {
            b.setEnabled(true);
            b.setText("Confirm");
        }, 3000); // 1000 milliseconds = 1 second
    }


}
