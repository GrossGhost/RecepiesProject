package com.receiptsproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import com.receiptsproject.dialogs.AddCategoriesDialog;
import com.receiptsproject.objects.CategoriesData;
import com.receiptsproject.util.RecyclerItemClickListener;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoriesFragment extends Fragment {

    public static int lastItemDeleted = -1;

    private Realm realm;
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private android.app.DialogFragment dialogFragment;
    private RealmResults<CategoriesData> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        dialogFragment = new AddCategoriesDialog();
        data = realm.where(CategoriesData.class).findAll();
        adapter = new CategoriesAdapter(data);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Fragment receiptsFragment = new ReceiptsFragment();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.categories_container, receiptsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                lastItemDeleted = position;
                onCreateDialog().show();

            }
        }));
        FloatingActionButton fab = view.findViewById(R.id.categories_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getFragmentManager(), "dialog");
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    protected Dialog onCreateDialog(){
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("Delete Category");
        adb.setMessage("Are you sure wanna delete category '" + data.get(lastItemDeleted).getName() + "'");
        adb.setPositiveButton("Delete", myClickListener);
        adb.setNegativeButton("Cancel", myClickListener);
        return adb.create();
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case Dialog.BUTTON_POSITIVE:
                    realm.beginTransaction();
                    data.get(lastItemDeleted).deleteFromRealm();
                    realm.commitTransaction();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
