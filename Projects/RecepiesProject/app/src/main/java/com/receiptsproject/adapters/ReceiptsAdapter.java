package com.receiptsproject.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.receiptsproject.R;
import com.receiptsproject.objects.ReceiptItemObject;

import java.util.List;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyAdapter>{

    private Context context;
    private List<ReceiptItemObject> receipts;

    @Override
    public ReceiptsAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.receipt_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReceiptsAdapter.MyAdapter holder, int position) {
        holder.title.setText(receipts.get(position).getTitle());
        holder.category.setText(receipts.get(position).getCategory());
        //holder.photo.setImageResource();

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "FULL PHOTO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView title;
        private TextView category;
        private CardView itemCardView;
        public MyAdapter(View itemView) {
            super(itemView);

            photo = (ImageView)itemView.findViewById(R.id.item_photo);
            title = (TextView)itemView.findViewById(R.id.item_title);
            category = (TextView)itemView.findViewById(R.id.item_category);
            itemCardView = (CardView)itemView.findViewById(R.id.receipt_card_view);
        }
    }
}
