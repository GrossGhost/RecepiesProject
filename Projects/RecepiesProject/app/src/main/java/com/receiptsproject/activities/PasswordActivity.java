package com.receiptsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.receiptsproject.R;
import com.receiptsproject.util.PasswordManager;

import static com.receiptsproject.util.Consts.IS_PASSWORD_SET;
import static com.receiptsproject.util.Consts.SHARED_NAME;
import static com.receiptsproject.util.Consts.TOKEN;

public class PasswordActivity extends AppCompatActivity {

    private EditText password;
    private TextView wrongPassword;
    private Button accept;
    private Context context;
    SharedPreferences sp = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        context = this;

        if (!sp.getBoolean(IS_PASSWORD_SET, false)){
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }

        wrongPassword = (TextView) findViewById(R.id.wrong_password);
        password = (EditText) findViewById(R.id.password);
        accept = (Button) findViewById(R.id.accept_password);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mPassword = password.getText().toString();
                String token = sp.getString(TOKEN, "");
                if (PasswordManager.validate(mPassword, token)){
                    wrongPassword.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }else{
                    wrongPassword.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
