package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 21.10.2017.
 */

public enum Category {

    DEFAULT("DEFAULT"), GREETINGS("GREETINGS"), INTRODUCTIONS("INTRODUCTIONS"), HELPING("HELPING"), GENERAL("GENERAL"),
    SHOPPING("SHOPPING"), ADDRESS("ADDRESS"), FOOD("FOOD"), TRAVEL("TRAVEL"), ACCOMODATION("ACCOMODATION"),
    TELEPHONE("TELEPHONE"), FRIEND("FRIEND"), HEALTH("HEALTH"), BUSINESS("BUSINESS"), MONEY("MONEY"), EDUCATION("EDUCATION");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
