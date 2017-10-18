package com.receiptsproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.receiptsproject.R;
import com.squareup.picasso.Picasso;


public class FullPhoto extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);

        imageView = (ImageView)findViewById(R.id.full_image);
        Intent intent = getIntent();
        String img = intent.getStringExtra("photo");
        Uri uri = Uri.parse(img);
        Picasso.with(this).load(uri).into(imageView);
    }
}
