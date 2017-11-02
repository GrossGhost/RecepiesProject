package com.receiptsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.CloudStorage;
import com.receiptsproject.DropboxManager;
import com.receiptsproject.R;
import com.receiptsproject.objects.ReceiptItemObject;
import com.receiptsproject.util.Consts;


public class DropboxActivity extends AppCompatActivity {


    private TextView dropboxStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);

        dropboxStatus =  (TextView) findViewById(R.id.tv_dropbox_login_status);

        Button dropboxTest = (Button) findViewById(R.id.btn_dropbox_test);
        dropboxTest.setOnClickListener(new StartTest());


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
            new LogoutDropbox().execute();
        }
    }
    private class LogoutDropbox extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
           try {
               getDropbox().logout();
               SharedPreferences sharedPreferences = getSharedPreferences(Consts.DROPBOX_PREF, Context.MODE_PRIVATE);
               SharedPreferences.Editor ed = sharedPreferences.edit();

               ed.remove("dropboxPersistent");
               ed.apply();
               CloudRail.clearAuthenticationResponse();
               return "Has been logout";
           }catch (Exception e){
               return "Error";
           }
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            dropboxStatus.setText("Not connected");
        }
    }


    private class GetUserName extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                return "Loggined as " + getDropbox().getUserName();
            }catch (Exception e){
                return "Not connected";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            dropboxStatus.setText(s);
        }
    }
}
