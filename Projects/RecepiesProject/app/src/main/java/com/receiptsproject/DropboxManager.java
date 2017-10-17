package com.receiptsproject;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.exceptions.ParseException;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.services.Dropbox;
import com.receiptsproject.util.Consts;

import java.util.concurrent.atomic.AtomicReference;

public class DropboxManager {

    private final static String AUTH_DATA = "AUTH_DATA";
    private final static String LOGIN_PENDING = "LOGIN_PENDING";

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
        CloudRail.setAdvancedAuthenticationMode(true);
        this.initDropbox();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Consts.DROPBOX_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String persistent = sharedPreferences.getString(AUTH_DATA, null);
        Log.d("persistant", persistent+"" );
        if (persistent != null){

            try {
                dropbox.get().loadAsString(persistent);
            }catch (ParseException e) {

                editor.remove(AUTH_DATA);
                editor.apply();
            }
        } else  if (sharedPreferences.getBoolean(LOGIN_PENDING, false)){
            // If a login is pending, continue it
            // Set pending login to false
            final SharedPreferences.Editor ed = sharedPreferences.edit();
            editor.putBoolean(LOGIN_PENDING, false);
            editor.apply();

            // Set result and continue login
            CloudRail.setAuthenticationResponse(context.getIntent());
            new Thread() {
                @Override
                public void run() {
                    getDropbox().login();

                    ed.putString(AUTH_DATA, getDropbox().saveAsString());
                    ed.apply();

                }
            }.start();
        } else{
            // Set the login to pending so that we now that a login process has been started
            editor.putBoolean(LOGIN_PENDING, true);
            editor.apply();
            new Thread() {
                @Override
                public void run() {
                    getDropbox().login();
                }
            }.start();
        }

    }

    public CloudStorage getDropbox() {
        return this.dropbox.get();
    }

}
