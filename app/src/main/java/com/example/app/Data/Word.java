package com.example.app.Data;

public class Word {

    public String knownLang;
    public String learnLang;

    public Word(String known, String learn) {
        this.knownLang = known;
        this.learnLang = learn;
    }

    public String getKnownLang() { return this.knownLang; }
    public void setKnownLang(String newWord) { this.knownLang = newWord; }

    public String getLearnLang() { return this.learnLang; }
    public void setLearnLang(String newWord) {this.learnLang = newWord; }
}
