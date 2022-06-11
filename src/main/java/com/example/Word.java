package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Word {

    String word;
    String word_en;
    String type;
    String[] singular;
    String[] plural;
    ArrayList<Definition> definitions;

    // Getter si setter

    public void setWord(String word) {
        this.word = word;
    }

    public void setWordEN(String word_en) {
        this.word_en = word_en;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSingular(String singular , int index) {
        this.singular[index] = singular;
    }

    public void setPlural(String plural , int index) {
        this.plural[index] = plural;
    }

    public String getWord() {
        return this.word;
    }

    public String getWordEN() {
        return this.word_en;
    }

    public String getType() {
        return this.type;
    }

    public String getSingular(int index) {
        return this.singular[index];
    }

    public String getPlural(int index) {
        return this.plural[index];
    }
    
    @Override
    public String toString() {
        return this.word + " " + this.word_en + " " + this.type + " " + Arrays.toString(this.singular) + " " + Arrays.toString(this.plural) + "\n" + this.definitions;
    }
}

class Definition  {
    String dict;
    String dictType;
    int year;
    String[] text;

    @Override
    public String toString() {
        return this.dict + " " + this.dictType + this.year + " " + Arrays.toString(this.text) + "\n";
    }
}


