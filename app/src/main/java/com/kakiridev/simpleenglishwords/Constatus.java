package com.kakiridev.simpleenglishwords;

import java.util.ArrayList;

/**
 * loged user
 * users list
 * words list
 * known list
 * unknown list
 *
 * known words < 90
 * +score
 * knonw percent
 * ranking position
 *
 * **/


public class Constatus {

    /** SETTINGS **/
    public static int numberOfMinWords = 20;


    public static User LOGGED_USER = new User();
    public static ArrayList<User> USER_LIST = new ArrayList<User>();
    public static ArrayList<Word> WORD_LIST = new ArrayList<Word>();

    public static ArrayList<Word> KNOWN_WORD_LIST = new ArrayList<Word>();
    public static ArrayList<Word> UNKNOWN_WORD_LIST = new ArrayList<Word>();


    public static ArrayList<Word> WORD_COMPLITED_LIST = new ArrayList<Word>();


    public static int getUncomplitedWords(){
        int wordsCount = 0;
        for (Word uw : KNOWN_WORD_LIST){
            if(uw.getscore() < 90){
                wordsCount++;
            }
        }

        return wordsCount;
    }

    public static int getScore(){
        int score = 0;

        for (Word kw : KNOWN_WORD_LIST){
            score = score + kw.getscore();
        }

        return score;
    }

    public static double getKnownPercent(){
        return (getScore() / (WORD_LIST.size()*100));
    }

    public static int getKnownCount(){
        return (KNOWN_WORD_LIST.size());
    }


}
