package com.receiptsproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.receiptsproject.R;

import java.io.File;


public class FullPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);

        ImageView imageView = (ImageView)findViewById(R.id.full_image);

        File photo = new File(getIntent().getStringExtra("photo"));

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("file://" + getIntent().getStringExtra("photo"),imageView);

    }
}
