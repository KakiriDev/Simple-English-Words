package com.kakiridev.simpleenglishwords;

//public class Word implements Parcelable{
public class Word{

    /** PARCELABLE
    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override public Word createFromParcel(Parcel parcel) {
            return new Word(parcel);
        }

        @Override public Word[] newArray(int size) {
            return new Word[size];
        }
    };
     **/
    public String id;
    public String nazwaPl;
    public String nazwaEn;
    public String category;
    public String score;

    /** PARCELABLE
    public Word(Parcel in) {
        //kolejnosc wczytywania musi byc taka sama jak zapisywania
        mId = in.readString();
        mNazwaPl = in.readString();
        mNazwaEn = in.readString();
        mCategory = in.readString();
    }
     **/



    public Word(){

    }

    public Word(String id, String nazwaPl, String nazwaEn, String category) {

        this.id = id;
        this.nazwaPl = nazwaPl;
        this.nazwaEn = nazwaEn;
        this.category = category;
    }

    public Word(String id, String nazwaPl, String nazwaEn, String category, String score) {

        this.id = id;
        this.nazwaPl = nazwaPl;
        this.nazwaEn = nazwaEn;
        this.category = category;
        this.score = score;
    }

    public String getmNazwaPl() {
        return nazwaPl;
    }

    public void setmNazwaPl(String mNazwaPl) {
        this.nazwaPl = mNazwaPl;
    }

    public String getmNazwaEn() {
        return nazwaEn;
    }

    public void setmNazwaEn(String mNazwaEn) {
        this.nazwaEn = mNazwaEn;
    }

    public String getmId() {
        return id;
    }

    public void setmId(String mId) {
        this.id = mId;
    }

    public String getmCategory() {
        return category;
    }

    public void setmCategory(String mCategory) {
        this.category = mCategory;
    }

    public String getmScore() {
        return score;
    }

    public void setmScore(String mScore) {
        this.score = mScore;
    }

/** PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(mId);
        parcel.writeString(mNazwaPl);
        parcel.writeString(mNazwaEn);
        parcel.writeString(mCategory);
    }
    **/
}