package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 21.10.2017.
 */

public class DailyPharase {
    private String phrase;
    private String meaning;
    private Category category;

    public DailyPharase(String phrase, String meaning, Category category) {
        this.phrase = phrase;
        this.meaning = meaning;
        this.category = category;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getMeaning() {
        return meaning;
    }

    public Category getCategory() {
        return category;
    }
}
