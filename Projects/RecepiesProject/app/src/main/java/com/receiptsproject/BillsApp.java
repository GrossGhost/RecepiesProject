package com.receiptsproject;

import android.app.Application;

import io.realm.Realm;


public class BillsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());

    }
}
