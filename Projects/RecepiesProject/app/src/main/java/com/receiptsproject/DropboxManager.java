package com.receiptsproject;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.exceptions.ParseException;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.services.Dropbox;
import com.receiptsproject.util.Consts;

import java.util.concurrent.atomic.AtomicReference;

public class DropboxManager {

    private final static DropboxManager ourInstance = new DropboxManager();

    private final AtomicReference<CloudStorage> dropbox = new AtomicReference<>();

    private Activity context = null;

    public static DropboxManager getInstance() {
        return ourInstance;
    }

    private DropboxManager() {
    }

    private void initDropbox() {

        Dropbox db = new Dropbox(context, Consts.APP_KEY_DROPBOX, Consts.APP_SECRET);

        dropbox.set(db);
    }

    // --------- Public Methods -----------
    public void prepare(Activity context) {
        this.context = context;

        CloudRail.setAppKey(Consts.APP_SECRET_CLOUDRAIL);
        CloudRail.setAdvancedAuthenticationMode(false);


        this.initDropbox();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.DROPBOX_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        try {
            String persistent = sharedPreferences.getString("dropboxPersistent", null);
            if (persistent != null) dropbox.get().loadAsString(persistent);
        } catch (ParseException e) {
            ed.remove("dropboxPersistent");
            ed.apply();
        }

        new Thread() {
            @Override
            public void run() {
                getDropbox().login();
            }
        }.start();

    }

    public CloudStorage getDropbox() {
        return this.dropbox.get();
    }

    public void storePersistent() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.DROPBOX_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("dropboxPersistent", dropbox.get().saveAsString());
        ed.apply();
    }


}
