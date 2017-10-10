package com.receiptsproject;

import android.content.Context;
import android.os.AsyncTask;

import com.cloudrail.si.services.Dropbox;
import com.receiptsproject.util.Consts;


public class LoginToDropboxAsyncTask extends AsyncTask {

    private Context context;

    public LoginToDropboxAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Dropbox dropbox = new Dropbox(context,
                Consts.APP_KEY_DROPBOX,
                Consts.APP_SECRET,
                "https://auth.cloudrail.com/com.receiptsproject",
                "");

        dropbox.login();

        return null;
    }

}
