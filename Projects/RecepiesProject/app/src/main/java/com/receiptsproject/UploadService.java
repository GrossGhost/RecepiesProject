package com.receiptsproject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cloudrail.si.interfaces.CloudStorage;
import com.receiptsproject.util.Consts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class UploadService extends Service {

    private boolean isDownloaded = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            new Upload(
                    intent.getStringExtra(Consts.CATEGORY),
                    intent.getStringExtra(Consts.NAME),
                    intent.getStringExtra(Consts.URI)
            ).execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private class Upload extends AsyncTask<String, Void, String> {

        private CloudStorage dropbox;

        private String folder, path;
        private InputStream stream;
        private long size;

        Upload(String category, String name, String uri) throws FileNotFoundException {

            this.dropbox = DropboxManager.getInstance().getDropbox();
            folder = "/" + category;
            path = folder + "/" + name + ".jpg";
            // test uri
            File photo = new File(uri);
            Log.d("uriFile", uri);
            stream = new FileInputStream(photo);
            size = 2048L;

        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                dropbox.createFolder(folder);
            } catch (Exception e) {
                e.printStackTrace();
           }
            try {
                dropbox.upload(path, stream, size, true);
            } catch (Exception e) {
                isDownloaded = false;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isDownloaded) {
                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Uploading Error", Toast.LENGTH_LONG).show();
            }

            stopSelf();
            super.onPostExecute(s);
        }
    }

}

