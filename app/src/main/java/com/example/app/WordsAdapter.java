package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.Data.Word;
import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordViewHolder>
{

    private final List<Word> words;

    public WordsAdapter(List<Word> words) {
        this.words = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position)
    {
        Word word = words.get(position);
        holder.knownLangInput.setText(word.getKnownLang());
        holder.learnLangInput.setText(word.getLearnLang());

        // Update word object with any text changes
        holder.knownLangInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) word.setKnownLang(holder.knownLangInput.getText().toString().trim());
        });
        holder.learnLangInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) word.setLearnLang(holder.learnLangInput.getText().toString().trim());
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder
    {
        EditText knownLangInput;
        EditText learnLangInput;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            knownLangInput = itemView.findViewById(R.id.knownLangInput);
            learnLangInput = itemView.findViewById(R.id.learnLangInput);
        }
    }
}
