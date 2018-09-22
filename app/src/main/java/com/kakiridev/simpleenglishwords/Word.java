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
    public int score = 0;

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

    public Word(String id, String nazwaPl, String nazwaEn, String category, int score) {

        this.id = id;
        this.nazwaPl = nazwaPl;
        this.nazwaEn = nazwaEn;
        this.category = category;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", nazwaPl='" + nazwaPl + '\'' +
                ", nazwaEn='" + nazwaEn + '\'' +
                ", category='" + category + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getnazwaPl() {
        return nazwaPl;
    }

    public void setnazwaPl(String mNazwaPl) {
        this.nazwaPl = mNazwaPl;
    }

    public String getnazwaEn() {
        return nazwaEn;
    }

    public void setnazwaEn(String mNazwaEn) {
        this.nazwaEn = mNazwaEn;
    }

    public String getid() {
        return id;
    }

    public void setid(String mId) {
        this.id = mId;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String mCategory) {
        this.category = mCategory;
    }

    public int getscore() {
        return score;
    }

    public void setscore(int mScore) {
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