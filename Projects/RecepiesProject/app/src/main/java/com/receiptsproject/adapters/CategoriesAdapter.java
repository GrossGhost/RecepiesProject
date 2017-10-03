package com.receiptsproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyAdapter>{

    @Override
    public CategoriesAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.MyAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        public MyAdapter(View itemView) {
            super(itemView);
        }
    }
}
