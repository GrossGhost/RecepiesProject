package com.receiptsproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.receiptsproject.R;
import com.receiptsproject.activities.AddReceiptActivity;
import com.receiptsproject.objects.ReceiptItemObject;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsFragment extends Fragment {

    //todo deleting receipts

    private final String TAG = "ReceiptsFragment/";
    private Context context;
    private List<ReceiptItemObject> receipts = new ArrayList<>();




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
                startActivity(new Intent(getActivity(), AddReceiptActivity.class));
            }
        });
    }
}
