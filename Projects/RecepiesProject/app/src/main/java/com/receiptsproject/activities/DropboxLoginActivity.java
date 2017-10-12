package com.receiptsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.exceptions.ParseException;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.services.Dropbox;
import com.receiptsproject.R;
import com.receiptsproject.util.Consts;

public class DropboxLoginActivity extends AppCompatActivity {

    private final static String LOGIN_PENDING = "LOGIN_PENDING";
    private final static String AUTH_DATA = "AUTH_DATA";

    private CloudStorage cs = new Dropbox(this, Consts.APP_KEY_DROPBOX, Consts.APP_SECRET,
            "https://auth.cloudrail.com/com.receiptsproject", "some_state");

    private Button dropboxLogin, dropboxLogout;
    private TextView dropboxStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_login);

        // Configure CloudRail
        CloudRail.setAppKey(Consts.APP_SECRET_CLOUDRAIL);
        CloudRail.setAdvancedAuthenticationMode(false);

        dropboxStatus =  (TextView) findViewById(R.id.tv_dropbox_login_status);

        dropboxLogin = (Button) findViewById(R.id.btn_dropbox_login);
        dropboxLogin.setOnClickListener(new StartLogin());

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        if (sharedPreferences.getString(AUTH_DATA, null) != null) {
            /* If there is already a logged in user, load data */

            try {
                cs.loadAsString(sharedPreferences.getString(AUTH_DATA, null));
                new GetUserName().execute();
            } catch (ParseException e) {
                // If parsing fails, remove user data -> new login required
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(AUTH_DATA);
                editor.apply();
            }
        } else if (sharedPreferences.getBoolean(LOGIN_PENDING, false)) {
            /* If a login is pending, continue it */

            // Set pending login to false
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(LOGIN_PENDING, false);
            editor.apply();

            // Set result and continue login
            CloudRail.setAuthenticationResponse(getIntent());
            new Thread() {
                @Override
                public void run() {
                    cs.login();

                    editor.putString(AUTH_DATA, cs.saveAsString());
                    editor.apply();

                    new GetUserName().execute();
                }
            }.start();
        } else {
            dropboxStatus.setText(R.string.not_logged_in);
        }
    }

    public void onContinueClick(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private class StartLogin implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Set the login to pending so that we now that a login process has been started
            editor.putBoolean(LOGIN_PENDING, true);
            editor.apply();

            // Start the login process
            new Thread() {
                @Override
                public void run() {
                    cs.login();
                }
            }.start();
        }
    }
    private class GetUserName extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            return cs.getUserName();
        }

        @Override
        protected void onPostExecute(String s) {
            dropboxStatus.setText(getString(R.string.logged_in_as) + s);
        }
    }
}


