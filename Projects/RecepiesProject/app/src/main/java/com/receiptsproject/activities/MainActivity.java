package com.receiptsproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cloudrail.si.CloudRail;
import com.receiptsproject.LoginToDropboxAsyncTask;
import com.receiptsproject.R;
import com.receiptsproject.fragments.CategoriesFragment;
import com.receiptsproject.util.Consts;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {



    //todo starting with CategoriesFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(getApplicationContext());

        if (savedInstanceState == null){
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.categories_container, new CategoriesFragment())
                    .commit();
        }

        CloudRail.setAppKey(Consts.APP_SECRET_CLOUDRAIL);
        CloudRail.setAdvancedAuthenticationMode(true);
        LoginToDropboxAsyncTask task = new LoginToDropboxAsyncTask(this.getApplicationContext());

        task.execute();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
