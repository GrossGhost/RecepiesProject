package com.receiptsproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.receiptsproject.R;
import com.receiptsproject.objects.ReceiptItemObject;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;

import static com.receiptsproject.util.Consts.TAKE_PICTURE;

public class AddReceiptActivity extends AppCompatActivity {

    private String TAG = "ADDRECEIPT";
    private String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE };
    private ImageView photo;
    private Button save;
    private EditText title;
    private static int REQUEST_CODE = 10;
    private Uri mOutputFileUri;
    private String filename;
    private Context context;
    private Activity activity;
    private Realm realm;
    private boolean wasClicked = false;

    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receite);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        photo = (ImageView) findViewById(R.id.imageView);
        save = (Button) findViewById(R.id.button);
        title = (EditText) findViewById(R.id.title);

        if (mOutputFileUri != null){
            Picasso.with(context).load(mOutputFileUri).into(photo);
        }
        context = this;
        activity = this;

        realm = Realm.getDefaultInstance();

        String buttonText = wasClicked ? "Save" : "Take a Photo";
        save.setText(buttonText);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!wasClicked) {
                    takePhoto();
                    wasClicked = true;
                    save.setText("Save");
                }
                else {
                    writeData(filename, category, mOutputFileUri);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults[0] == RESULT_OK
                    && grantResults[1] == RESULT_OK){
                saveFullImage(
                        title.getText().toString()
                );
            }
        }
    }


    private void takePhoto(){
        Log.d(TAG, "take photo");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED){
            if (
                    !title.getText().toString().equals(null)
                    ){
                saveFullImage(
                        title.getText().toString()
                );
            }
        }else {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_CODE);
        }
    }
    private void saveFullImage(String title) {
        Log.d(TAG, "saveFullImage");
        this.filename = title;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), String.valueOf(filename));
        mOutputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            Picasso.with(context)
                    .load(mOutputFileUri)
                    .into(photo);

        }
    }

    private void writeData(String title, String category, Uri img){
        realm.beginTransaction();
        String image = String.valueOf(img);
        ReceiptItemObject receipt = new ReceiptItemObject(title, category, image);
        realm.insert(receipt);
        realm.commitTransaction();
    }
}
