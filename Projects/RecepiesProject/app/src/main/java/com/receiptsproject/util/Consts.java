package com.receiptsproject.util;


import java.util.ArrayList;
import java.util.Arrays;

public class Consts {

    //For SharedPreferences
    public static final String SHARED_NAME = "My shared preferences";
    public static final String TOKEN = "token";
    public static final String IS_PASSWORD_SET = "is password set";

    //For Settings
    public static final String BUNDLE_DATA = "Bundle name";
    public static final String SET = "Set password";
    public static final String CHANGE = "Change password";
    public static final String DELETE = "Delete password";

    //For writing files
    public static final String FILE_NAME = "AwesomeFile";

    //For Categories
    public static final ArrayList<String> CATEGORIES_LIST = new ArrayList<>(Arrays.asList(
            "Documents","Grocery bills","Restaurant bills","Electronic items"));
}
