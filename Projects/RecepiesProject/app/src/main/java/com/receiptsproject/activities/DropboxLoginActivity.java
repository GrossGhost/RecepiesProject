package com.receiptsproject.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudrail.si.interfaces.CloudStorage;
import com.receiptsproject.DropboxManager;
import com.receiptsproject.R;
import com.receiptsproject.objects.ReceiptItemObject;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class DropboxLoginActivity extends AppCompatActivity {

    private TextView dropboxStatus;
    private ReceiptItemObject test;
    private Uri testUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_login);

        dropboxStatus =  (TextView) findViewById(R.id.tv_dropbox_login_status);

        Button dropboxTest = (Button) findViewById(R.id.btn_dropbox_test);
        dropboxTest.setOnClickListener(new StartTest());

        Resources resources = getResources();
        testUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.test));
        test = new ReceiptItemObject("Test", "Category", testUri);



    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUserName().execute();
    }

    private CloudStorage getDropbox() {
        return DropboxManager.getInstance().getDropbox();
    }

    public void onContinueClick(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private class StartTest implements View.OnClickListener{
        String s;
        @Override
        public void onClick(View view) {
            new Thread() {
                @Override
                public void run() {
                    s = getDropbox().getUserLogin();

                    InputStream fs = null;
                    long size = -1;
                    try {
                        //fs = getContentResolver().openInputStream(testUri);
                        fs = getApplicationContext().getResources().openRawResource(+ R.drawable.test);
                        size = getContentResolver().openAssetFileDescriptor(testUri, "r").getLength();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //getDropbox().upload("/myFolder/myFile.jpg", fs, 1024L, true);
                    try{
                        getDropbox().createFolder("/test");
                    } catch (Exception e){

                    }
                    try{
                        getDropbox().upload("/test/myFile.jpg", fs, 1024L, true);
                    } catch (Exception e){

                    }


                }

            }.start();
            Toast.makeText(getApplicationContext(),s + " ",Toast.LENGTH_SHORT).show();
        }
    }

    private class GetUserName extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            return getDropbox().getUserName();
        }

        @Override
        protected void onPostExecute(String s) {
            dropboxStatus.setText(getString(R.string.logged_in_as) + s);
        }
    }
}


