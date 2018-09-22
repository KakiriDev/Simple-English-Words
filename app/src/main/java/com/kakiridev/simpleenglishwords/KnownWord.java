package com.kakiridev.simpleenglishwords;

public class KnownWord extends Word {

    private int Score;
    private String Id;

    public KnownWord(){

    }

    public KnownWord(String id, int score) {
        this.Id = id;
        this.Score = score;
    }

    public int getscore() {
        return Score;
    }

    public void setscore(int mScore) {
        this.Score = mScore;
    }

    public String getmId() {
        return Id;
    }

    public void setid(String mId) {
        this.Id = mId;
    }


}
