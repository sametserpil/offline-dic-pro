package com.samet.offlinedic.pro.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by samet on 28.09.2017.
 */

public class WordSuggestion implements SearchSuggestion {

    private String word;

    public WordSuggestion(String word) {
        this.word = word;
    }

    public WordSuggestion(Parcel source) {
        this.word = source.readString();
    }

    @Override
    public String getBody() {
        return word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
    }

    public static final Creator<WordSuggestion> CREATOR = new Creator<WordSuggestion>() {
        @Override
        public WordSuggestion createFromParcel(Parcel in) {
            return new WordSuggestion(in);
        }

        @Override
        public WordSuggestion[] newArray(int size) {
            return new WordSuggestion[size];
        }
    };
}
