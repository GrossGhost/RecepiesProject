package com.receiptsproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.receiptsproject.R;
import com.receiptsproject.objects.CategoriesData;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyAdapter>{

    private final RealmResults<CategoriesData> data;
    private Realm realm;

    public CategoriesAdapter(RealmResults<CategoriesData> data){
        this.data = data;
        realm = Realm.getDefaultInstance();
    }
    @Override
    public CategoriesAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(final CategoriesAdapter.MyAdapter holder, int position) {

        holder.categoryText.setText(data.get(position).getName());
        holder.deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                data.get(holder.getAdapterPosition()).deleteFromRealm();
                realm.commitTransaction();
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyAdapter extends RecyclerView.ViewHolder {

        TextView categoryText;
        ImageButton deleteCategoryButton;

        MyAdapter(final View view) {
            super(view);
            categoryText = view.findViewById(R.id.item_category_title);
            deleteCategoryButton = view.findViewById(R.id.item_delete_button);

        }
    }
}
