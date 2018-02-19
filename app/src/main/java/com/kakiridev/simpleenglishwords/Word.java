package com.kakiridev.simpleenglishwords;

import android.os.Parcel;
import android.os.Parcelable;

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
    public String mId;
    public String mNazwaPl;
    public String mNazwaEn;
    public String mCategory;
    public String mScore;

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

        this.mId = id;
        this.mNazwaPl = nazwaPl;
        this.mNazwaEn = nazwaEn;
        this.mCategory = category;
    }

    public Word(String id, String nazwaPl, String nazwaEn, String category, String score) {

        this.mId = id;
        this.mNazwaPl = nazwaPl;
        this.mNazwaEn = nazwaEn;
        this.mCategory = category;
        this.mScore = score;
    }

    public String getmNazwaPl() {
        return mNazwaPl;
    }

    public String getmNazwaEn() {
        return mNazwaEn;
    }

    public void setmNazwaPl(String mNazwaPl) {
        this.mNazwaPl = mNazwaPl;
    }

    public void setmNazwaEn(String mNazwaEn) {
        this.mNazwaEn = mNazwaEn;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
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