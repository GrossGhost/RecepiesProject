package com.receiptsproject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cloudrail.si.interfaces.CloudStorage;
import com.receiptsproject.objects.ReceiptItemObject;
import com.receiptsproject.retrofit.RestManager;
import com.receiptsproject.retrofit.ShortenerBody;
import com.receiptsproject.retrofit.ShortenerResponse;
import com.receiptsproject.util.Consts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        private String folder, path, shareLink, shorterLink;
        private InputStream stream;
        private long size;

        private Realm realm;
        private ReceiptItemObject item;

        Upload(String category, String name, String uri) throws FileNotFoundException {

            this.dropbox = DropboxManager.getInstance().getDropbox();
            folder = "/BillsApp/" + category;
            path = folder + "/" + name + ".jpg";
            File photo = new File(uri);
            Log.d("uriFile", uri);
            stream = new FileInputStream(photo);
            size = 2048L;

            realm = Realm.getDefaultInstance();
            item = realm.where(ReceiptItemObject.class).equalTo("title", name).equalTo("category", category).findFirst();

        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                dropbox.createFolder("/BillsApp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                dropbox.createFolder(folder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                dropbox.upload(path, stream, size, false);
            } catch (Exception e) {
                isDownloaded = false;
                e.printStackTrace();
            }
            try {
                shareLink = dropbox.createShareLink(path);
                createShorterLink(shareLink);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void createShorterLink(String longLink) {

            ShortenerBody body = new ShortenerBody();
            body.setLongUrl(longLink);

            Call<ShortenerResponse> call = RestManager.getApiService().registerUser(body, Consts.URL_SHORTENER_API_KEY);
            call.enqueue(new Callback<ShortenerResponse>() {
                @Override
                public void onResponse(Call<ShortenerResponse> call, Response<ShortenerResponse> response) {

                    if (response.isSuccessful()) {
                        realm.beginTransaction();
                        item.setShorterLink(response.body().getId());
                        realm.commitTransaction();
                    }
                }

                @Override
                public void onFailure(Call<ShortenerResponse> call, Throwable t) {

                }
            });
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

