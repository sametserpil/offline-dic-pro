package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 25.10.2017.
 */

public enum Direction {
    TR2EN("TR2EN"), EN2TR("EN2TR");

    Direction(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
