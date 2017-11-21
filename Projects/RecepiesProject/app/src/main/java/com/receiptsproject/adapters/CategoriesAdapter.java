package com.receiptsproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.receiptsproject.R;
import com.receiptsproject.fragments.CategoriesFragment;
import com.receiptsproject.objects.CategoriesData;
import com.squareup.picasso.Picasso;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyAdapter> implements RealmChangeListener {

    private final RealmResults<CategoriesData> data;
    private int preChangeItemCount;
    private Context context;

    public CategoriesAdapter(Context context, RealmResults<CategoriesData> data) {
        this.context = context;
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
        String category = data.get(position).getName();

        switch (category) {
            case "Documents":
                Picasso.with(context).load(R.drawable.documents)
                        .resize(150,150).into(holder.categoryIcon);
                break;
            case "Grocery":
                Picasso.with(context).load(R.drawable.grocery)
                        .resize(150,150).into(holder.categoryIcon);
                break;
            case "Electronics":
                Picasso.with(context).load(R.drawable.electronic)
                        .resize(150,150).into(holder.categoryIcon);
                break;
            case "Restaurants":
                Picasso.with(context).load(R.drawable.restoraunt)
                        .resize(150,150).into(holder.categoryIcon);
                break;
            case "Others":
                Picasso.with(context).load(R.drawable.others)
                        .resize(150,150).into(holder.categoryIcon);
                break;
            default:
                Picasso.with(context).load(R.drawable.user)
                        .resize(150,150).into(holder.categoryIcon);
        }

        holder.categoryText.setText(category);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onChange(Object o) {

        if (preChangeItemCount < getItemCount()) {
            notifyDataSetChanged();
        } else
            notifyItemRemoved(CategoriesFragment.lastItemDeleted);

        preChangeItemCount = getItemCount();

    }

    class MyAdapter extends RecyclerView.ViewHolder {

        TextView categoryText;
        ImageView categoryIcon;

        MyAdapter(final View view) {
            super(view);
            categoryText = view.findViewById(R.id.item_category_title);
            categoryIcon = view.findViewById(R.id.item_category_icon);
        }
    }
}
