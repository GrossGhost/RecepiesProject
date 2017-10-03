package com.receiptsproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.receiptsproject.R;
import com.receiptsproject.adapters.CategoriesAdapter;
import com.receiptsproject.objects.CategoriesData;

import io.realm.Realm;

public class CategoriesFragment extends android.support.v4.app.Fragment {
    //todo create layout
    //todo adding new categories
    //todo deleting categories
    //todo add transition on ReceiptsFragment

    private Realm realm;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.categories_fragment, container, false);
        recyclerView = view.findViewById(R.id.categories_recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CategoriesAdapter(realm.where(CategoriesData.class).findAll()));

        FloatingActionButton fab = view.findViewById(R.id.categories_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CategoriesData newCategory = new CategoriesData();
                newCategory.setName("newCategory");

                realm.beginTransaction();
                realm.insert(newCategory);
                realm.commitTransaction();

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
