package com.kakiridev.simpleenglishwords;

import java.util.ArrayList;

public class Constatus {
    public static User LOGGED_USER = new User();  /** actually logged user **/
    public static ArrayList<User> USER_LIST = new ArrayList<User>(); /** list of users **/
    public static ArrayList<Word> WORD_LIST = new ArrayList<Word>(); /** list of all words **/
    public static ArrayList<Word> WORD_UNCOMPLITED_LIST = new ArrayList<Word>(); /** list of untranslated words **/
    public static ArrayList<Word> KNOWN_WORD_LIST = new ArrayList<Word>();
    public static ArrayList<Word> UNKNOWN_WORD_LIST = new ArrayList<Word>();
}
