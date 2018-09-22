package com.kakiridev.simpleenglishwords;

public class KnownWord extends Word {

    private int score;
    private String id;

    public KnownWord(){

    }

    public KnownWord(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getscore() {
        return score;
    }

    public void setscore(int mScore) {
        this.score = mScore;
    }

    public String getid() {
        return id;
    }

    public void setid(String mId) {
        this.id = mId;
    }


}
