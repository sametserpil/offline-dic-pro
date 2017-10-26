package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 26.10.2017.
 */

public class FavHis {

    private String word;
    private Direction direction;

    public FavHis(String word, Direction direction) {
        this.word = word;
        this.direction = direction;
    }

    public String getWord() {
        return word;
    }

    public Direction getDirection() {
        return direction;
    }
}
