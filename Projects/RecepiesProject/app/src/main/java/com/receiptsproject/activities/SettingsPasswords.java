package com.receiptsproject.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.receiptsproject.R;
import com.receiptsproject.util.PasswordManager;

import static com.receiptsproject.util.Consts.BUNDLE_DATA;
import static com.receiptsproject.util.Consts.CHANGE;
import static com.receiptsproject.util.Consts.DELETE;
import static com.receiptsproject.util.Consts.IS_PASSWORD_SET;
import static com.receiptsproject.util.Consts.SET;
import static com.receiptsproject.util.Consts.SHARED_NAME;
import static com.receiptsproject.util.Consts.TOKEN;


public class SettingsPasswords extends AppCompatActivity {

    private TextView wrongPassword;
    private EditText setPassword;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText deletePassword;
    private Button accept;
    private SharedPreferences sharedPreferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        String action = bundle.getString(BUNDLE_DATA);
        switch (action) {
            case SET:
                setContentView(R.layout.activity_set_passwords);
                set();
                break;
            case CHANGE:
                setContentView(R.layout.activity_change_passwords);
                change();
                break;
            case DELETE:
                setContentView(R.layout.activity_delete_passwords);
                delete();
                break;
        }
    }

    private void delete() {
        deletePassword = (EditText)findViewById(R.id.delete_password_editText);
        accept = (Button)findViewById(R.id.delete_accept_button);
        wrongPassword = (TextView) findViewById(R.id.wrong_del_password);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = deletePassword.getText().toString();
                if (PasswordManager.validate(password, sharedPreferences.getString(TOKEN, ""))){
                    wrongPassword.setVisibility(View.INVISIBLE);
                    sharedPreferences.edit()
                            .putString(TOKEN, null)
                            .putBoolean(IS_PASSWORD_SET, false)
                            .apply();
                    finish();
                } else {
                    wrongPassword.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void change() {
        oldPassword = (EditText)findViewById(R.id.old_password);
        newPassword = (EditText)findViewById(R.id.new_password);
        wrongPassword = (TextView) findViewById(R.id.wrong_change_pass);
        accept = (Button)findViewById(R.id.set_accept_button);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPasswordString = oldPassword.getText().toString();
                if (PasswordManager.validate(oldPasswordString, sharedPreferences.getString(TOKEN, ""))){
                    wrongPassword.setVisibility(View.INVISIBLE);
                    String token = PasswordManager.makeDigest(newPassword.getText().toString());
                    sharedPreferences.edit()
                            .putString(TOKEN, token)
                            .putBoolean(IS_PASSWORD_SET, true)
                            .apply();
                    finish();
                } else {
                    wrongPassword.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void set() {
        setPassword = (EditText)findViewById(R.id.set_password);
        accept = (Button)findViewById(R.id.set_accept_button);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = setPassword.getText().toString();
                String token = PasswordManager.makeDigest(password);
                sharedPreferences.edit()
                        .putString(TOKEN, token)
                        .putBoolean(IS_PASSWORD_SET, true)
                        .apply();
                finish();
            }
        });
    }
}
