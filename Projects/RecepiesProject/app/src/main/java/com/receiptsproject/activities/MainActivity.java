package com.receiptsproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cloudrail.si.CloudRail;
import com.receiptsproject.DropboxManager;
import com.receiptsproject.R;
import com.receiptsproject.fragments.CategoriesFragment;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            DropboxManager.getInstance().prepare(this);
        }catch (Exception e){
            Toast.makeText(this, "Cant login to dropbox", Toast.LENGTH_LONG).show();
        }

        if (savedInstanceState == null){
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.categories_container, new CategoriesFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            return true;
        }
        if (id == R.id.menu_dropbox) {
            startActivity(new Intent(this, DropboxActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        DropboxManager.getInstance().storePersistent();
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.getCategories().contains("android.intent.category.BROWSABLE")) {
            CloudRail.setAuthenticationResponse(intent);
        }
        super.onNewIntent(intent);
    }
}
