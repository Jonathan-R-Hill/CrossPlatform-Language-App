package com.example.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;


import com.example.app.Data.Word;
import com.example.app.Data.WordStorage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreateMultipleWords extends AppCompatActivity {
    private final List<Word> importedWords = new ArrayList<>();
    private Button importButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_multiple_words);

        importButton = findViewById(R.id.importButton);
        importButton.setOnClickListener(this::openFilePicker);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        importWordsFromFile(uri);
                    }
                }
            });

    // Open the file picker
    public void openFilePicker(View v) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        filePickerLauncher.launch(intent);
    }

    // Read the file and import words
    private void importWordsFromFile(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String knownLang = parts[0].trim();
                    String learnLang = parts[1].trim();
                    Word word = new Word(knownLang, learnLang);
                    importedWords.add(word);
                }
            }
            WordStorage.addMultipleWordsToLearning(this, importedWords);  // Save words to JSON
            Toast.makeText(this, "Words imported successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show();
        }
    }
}
