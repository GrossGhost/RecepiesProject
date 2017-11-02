package com.receiptsproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.receiptsproject.R;
import com.receiptsproject.activities.AddReceiptActivity;
import com.receiptsproject.adapters.ReceiptsAdapter;
import com.receiptsproject.objects.ReceiptItemObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReceiptsFragment extends Fragment {

    private final String TAG = "ReceiptsFragment/";
    private Context context;
    private String category;
    private Realm realm;
    private RealmResults<ReceiptItemObject> data;
    private ReceiptsAdapter adapter;
    private RecyclerView recyclerView;

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
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            category = bundle.getString("category");

        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_new_receipt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddReceiptActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.receipts_recycler);
        realm = Realm.getDefaultInstance();
        data = realm.where(ReceiptItemObject.class).equalTo("category", category).findAll();
        adapter = new ReceiptsAdapter(context, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        data = realm.where(ReceiptItemObject.class).equalTo("category", category).findAll();
        adapter = new ReceiptsAdapter(context, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }
}
