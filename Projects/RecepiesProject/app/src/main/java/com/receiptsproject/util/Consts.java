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
    public static final int TAKE_PICTURE = 1;

    //For Categories
    public static final ArrayList<String> CATEGORIES_LIST = new ArrayList<>(Arrays.asList(
            "Documents","Grocery","Electronics","Restaurants","Others"));

    //For DropBox
    public static final String APP_KEY_DROPBOX = "5g4xzheb9woqp1x";
    public static final String APP_SECRET = "h2wgoqvmkvsdv59";
    public static final String APP_SECRET_CLOUDRAIL = "59dcccaec8b6755b44690655";
    public static final String DROPBOX_PREF = "Dropbox preferences";
    public static boolean isLogined = false;

    //For UploadService
    public static final String CATEGORY = "category";
    public static final String NAME = "name";
    public static final String URI = "uri";
    public static final String URL_SHORTENER_API_KEY = "AIzaSyCe2rrN892Mpw3JEFMjOKV0MbCTT9iE9rc";
    public static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com";


}
