package com.receiptsproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.receiptsproject.R;
import com.receiptsproject.fragments.CategoriesFragment;
import com.receiptsproject.objects.CategoriesData;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyAdapter> implements RealmChangeListener {

    private final RealmResults<CategoriesData> data;
    private int preChangeItemCount;

    public CategoriesAdapter(RealmResults<CategoriesData> data) {
        this.data = data;
        this.data.addChangeListener(this);
        this.preChangeItemCount = data.size();
    }

    @Override
    public CategoriesAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(final CategoriesAdapter.MyAdapter holder, int position) {
        holder.categoryText.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onChange(Object o) {

        if (preChangeItemCount < getItemCount()){
            notifyItemInserted(getItemCount());
        } else
            notifyItemRemoved(CategoriesFragment.lastItemDeleted);

        preChangeItemCount = getItemCount();

    }

    class MyAdapter extends RecyclerView.ViewHolder {

        TextView categoryText;

        MyAdapter(final View view) {
            super(view);
            categoryText = view.findViewById(R.id.item_category_title);
        }
    }
}
