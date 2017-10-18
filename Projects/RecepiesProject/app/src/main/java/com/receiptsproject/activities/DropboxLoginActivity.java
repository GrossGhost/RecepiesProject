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
import com.receiptsproject.UploadService;
import com.receiptsproject.objects.ReceiptItemObject;
import com.receiptsproject.util.Consts;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class DropboxLoginActivity extends AppCompatActivity {

    private TextView dropboxStatus;
    private String strUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_login);

        dropboxStatus =  (TextView) findViewById(R.id.tv_dropbox_login_status);

        Button dropboxTest = (Button) findViewById(R.id.btn_dropbox_test);
        dropboxTest.setOnClickListener(new StartTest());

        Resources resources = getResources();
        strUri = "android.resource://com.receiptsproject/" + R.drawable.test;
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

        @Override
        public void onClick(View view) {

            Intent uploadServiceIntent = new Intent(getApplicationContext(), UploadService.class);
            uploadServiceIntent.putExtra(Consts.CATEGORY, "1fin_test1");
            uploadServiceIntent.putExtra(Consts.NAME, "1fin_name1");
            uploadServiceIntent.putExtra(Consts.URI, strUri);

            startService(uploadServiceIntent);

        }
    }

    private class GetUserName extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            try {
                return getDropbox().getUserName();
            }catch (Exception e){
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            dropboxStatus.setText(getString(R.string.logged_in_as) + s);
        }
    }
}


