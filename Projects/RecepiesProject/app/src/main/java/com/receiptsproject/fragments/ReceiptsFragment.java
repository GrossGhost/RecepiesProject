package com.receiptsproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.receiptsproject.R;

public class ReceiptsFragment extends Fragment {

    //todo adding new receipts
    //todo deleting receipts

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "---------------- onCreateView ------------" );
        return inflater.inflate(R.layout.receipts_fragment, container, false);
    }

    private final String TAG = "ReceiptsFragment/";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "---------------- onViewCreated ------------" );

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_new_receipt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Receipt", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

}
