package com.receiptsproject.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.receiptsproject.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ReceiptsFragment extends Fragment {

    //todo adding new receipts
    //todo deleting receipts

    private final String TAG = "ReceiptsFragment/";
    private final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private final int PERMISSIONS_KEY = 101;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "---------------- onCreateView ------------" );
        return inflater.inflate(R.layout.receipts_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "---------------- onViewCreated ------------" );
        context = getActivity();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_new_receipt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, PERMISSION) == PERMISSION_GRANTED){
                    addReceipt(context);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSIONS_KEY);
                }
            }
        });
    }

    private void addReceipt(Context context){
        //todo File write
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_KEY){
            if (grantResults[0] == PERMISSION_GRANTED){
                addReceipt(context);
            }
        }else{
            Toast.makeText(context, "You should allow this", Toast.LENGTH_SHORT).show();
        }
    }
}
