package com.receiptsproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.receiptsproject.R;
import com.receiptsproject.UploadService;
import com.receiptsproject.activities.FullPhoto;
import com.receiptsproject.objects.ReceiptItemObject;
import com.receiptsproject.util.Consts;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyAdapter>{

    private Context context;
    private RealmResults<ReceiptItemObject> receipts;

    public ReceiptsAdapter(Context context, RealmResults<ReceiptItemObject> receipts) {
        this.context = context;
        this.receipts = receipts;
    }

    @Override
    public ReceiptsAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.receipt_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReceiptsAdapter.MyAdapter holder, final int position) {

        holder.title.setText(receipts.get(position).getTitle());
        holder.category.setText(receipts.get(position).getCategory());
        File photo = new File(receipts.get(position).getImage());
        Picasso.with(context).load(photo)
                .resize(150, 150)
                .into(holder.photo);

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullPhoto.class);
                intent.putExtra("photo", receipts.get(position).getImage());
                //context.startActivity(intent);
            }
        });
        holder.itemCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(ReceiptItemObject.class).contains("image", receipts.get(position).getImage()).findAll().deleteFirstFromRealm();
                    }
                });
                notifyDataSetChanged();
                return false;
            }
        });

        holder.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uploadServiceIntent = new Intent(context, UploadService.class);
                uploadServiceIntent.putExtra(Consts.CATEGORY, receipts.get(position).getCategory());
                uploadServiceIntent.putExtra(Consts.NAME, receipts.get(position).getTitle());
                uploadServiceIntent.putExtra(Consts.URI, receipts.get(position).getImage());

                context.startService(uploadServiceIntent);
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
        private ImageButton uploadButton;
        public MyAdapter(View itemView) {
            super(itemView);

            photo = (ImageView)itemView.findViewById(R.id.item_photo);
            title = (TextView)itemView.findViewById(R.id.item_title);
            category = (TextView)itemView.findViewById(R.id.item_category);
            itemCardView = (CardView)itemView.findViewById(R.id.receipt_card_view);
            uploadButton = itemView.findViewById(R.id.button_upload);
        }
    }
}
