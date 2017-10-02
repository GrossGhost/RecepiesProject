package com.receiptsproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.receiptsproject.R;

import static com.receiptsproject.util.Consts.BUNDLE_DATA;
import static com.receiptsproject.util.Consts.CHANGE;
import static com.receiptsproject.util.Consts.DELETE;
import static com.receiptsproject.util.Consts.SET;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    LinearLayout set;
    LinearLayout change;
    LinearLayout delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        set = (LinearLayout) findViewById(R.id.set_password);
        change = (LinearLayout) findViewById(R.id.change_password);
        delete = (LinearLayout) findViewById(R.id.delete_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_password: {
                Intent i = new Intent(this, SettingsPasswords.class);
                i.putExtra(BUNDLE_DATA, SET);
                startActivity(i);
                break;
            }
            case R.id.change_password: {
                Intent i = new Intent(this, SettingsPasswords.class);
                i.putExtra(BUNDLE_DATA, CHANGE);
                startActivity(i);
                break;
            }
            case R.id.delete_password: {
                Intent i = new Intent(this, SettingsPasswords.class);
                i.putExtra(BUNDLE_DATA, DELETE);
                startActivity(i);
                break;
            }
        }
    }
}
