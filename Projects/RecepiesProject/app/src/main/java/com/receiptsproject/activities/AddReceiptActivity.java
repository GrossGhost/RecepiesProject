package com.receiptsproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.receiptsproject.R;
import com.receiptsproject.objects.ReceiptItemObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;

public class AddReceiptActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "ADDRECEIPT";

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_PERMISSIONS_CODE = 1;

    private ImageView photo;

    private Button take, save;
    private File photoFile;
    private EditText title;
    private String filename;
    private Realm realm;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receite);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        photo = (ImageView) findViewById(R.id.imageView);
        take = (Button) findViewById(R.id.button_take_photo);
        take.setOnClickListener(this);
        save = (Button) findViewById(R.id.button_save);
        save.setOnClickListener(this);
        title = (EditText) findViewById(R.id.title);

        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            takePhoto();
        }
    }


    private void takePhoto() {
        Log.d(TAG, "take photo");

        if (!title.getText().toString().equals("")) {
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            photoFile = null;

            try {
                photoFile = createImageFile(title.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String authorities = getApplicationContext().getPackageName() + ".fileprovider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
        }else{
            Toast.makeText(this,"Please, enter title of image", Toast.LENGTH_LONG).show();
        }

    }

    private File createImageFile(String title) throws IOException {
        Log.d(TAG, "saveFullImage");

        this.filename = title;
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(title, ".jpg", storageDirectory);

        return image;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            Picasso.with(this)
                    .load(photoFile.getAbsoluteFile())
                    .resize(320, 480)
                    .into(photo);

        }
    }

    private void writeData(String title, String category, File img) {
        realm.beginTransaction();
        String image = img.getAbsolutePath();
        ReceiptItemObject receipt = new ReceiptItemObject(title, category, image);
        realm.insert(receipt);
        realm.commitTransaction();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_take_photo :
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        takePhoto();
                    } else {
                        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE);

                    }

                } else {
                    takePhoto();
                }
                break;
            case R.id.button_save :
                if (photoFile != null){
                    writeData(filename, category, photoFile);
                    finish();
                } else{
                    Toast.makeText(this, "Take photo first!", Toast.LENGTH_LONG).show();
                }

                break;
        }


    }
}
