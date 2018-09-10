package com.kakiridev.simpleenglishwords;

public class KnownWord {

    private String mScore;
    private String mId;

    public KnownWord(){

    }

    public KnownWord(String id, String score) {
        this.mId = id;
        this.mScore = score;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


}
