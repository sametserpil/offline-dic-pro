package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 21.10.2017.
 */

public class PharasalVerb {
    private String word;
    private String meaning;
    private String example;

    public PharasalVerb(String word, String meaning, String example) {
        this.word = word;
        this.meaning = meaning;
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExample() {
        return example;
    }
}
