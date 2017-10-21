package com.samet.offlinedic.pro.model;

/**
 * Created by samet on 21.10.2017.
 */

public class IrregularVerb {

    private String word;
    private String pastSimple;
    private String pastParticiple;
    private String thirdPersonSingular;
    private String presentParticipleGerund;
    private String meaning;

    public IrregularVerb(String word, String pastSimple, String pastParticiple, String thirdPersonSingular, String presentParticipleGerund, String meaning) {
        this.word = word;
        this.pastSimple = pastSimple;
        this.pastParticiple = pastParticiple;
        this.thirdPersonSingular = thirdPersonSingular;
        this.presentParticipleGerund = presentParticipleGerund;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public String getPastSimple() {
        return pastSimple;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public String getThirdPersonSingular() {
        return thirdPersonSingular;
    }

    public String getPresentParticipleGerund() {
        return presentParticipleGerund;
    }

    public String getMeaning() {
        return meaning;
    }
}
