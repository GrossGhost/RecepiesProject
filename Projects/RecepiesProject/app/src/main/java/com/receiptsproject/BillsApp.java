package com.receiptsproject;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class BillsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }
}
