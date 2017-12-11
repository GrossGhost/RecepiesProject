package com.receiptsproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.receiptsproject.dialogs.CodeInfoDialog;
import com.receiptsproject.objects.ReceiptItemObject;
import com.receiptsproject.util.Consts;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyAdapter> implements RealmChangeListener{

    private Context context;
    private FragmentManager fm;
    private RealmResults<ReceiptItemObject> receipts;

    public ReceiptsAdapter(Context context, FragmentManager fm, RealmResults<ReceiptItemObject> receipts) {
        this.context = context;
        this.fm = fm;
        this.receipts = receipts;
        this.receipts.addChangeListener(this);
    }

    @Override
    public ReceiptsAdapter.MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.receipt_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ReceiptsAdapter.MyAdapter holder, int position) {

        final int pos = position;
        holder.title.setText(receipts.get(position).getTitle());
        holder.category.setText(receipts.get(position).getShorterId());
        File photo = new File(receipts.get(position).getImage());
        Picasso.with(context).load(photo)
                .resize(150, 150)
                .into(holder.photo);

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullPhoto.class);
                intent.putExtra("photo", receipts.get(pos).getImage());
                context.startActivity(intent);
            }
        });
        holder.itemCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(ReceiptItemObject.class).contains("image", receipts.get(pos).getImage()).findAll().deleteFirstFromRealm();
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
                uploadServiceIntent.putExtra(Consts.CATEGORY, receipts.get(pos).getCategory());
                uploadServiceIntent.putExtra(Consts.NAME, receipts.get(pos).getTitle());
                uploadServiceIntent.putExtra(Consts.URI, receipts.get(pos).getImage());

                context.startService(uploadServiceIntent);
            }
        });

        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeInfoDialog infoDialog = new CodeInfoDialog();
                Bundle args = new Bundle();
                args.putString("code", receipts.get(pos).getShorterId());
                infoDialog.setArguments(args);
                infoDialog.show(fm,"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    class MyAdapter extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView title;
        private TextView category;
        private CardView itemCardView;
        private ImageButton uploadButton;
        private ImageButton infoButton;

        MyAdapter(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.item_photo);
            title = itemView.findViewById(R.id.item_title);
            category = itemView.findViewById(R.id.item_category);
            itemCardView = itemView.findViewById(R.id.receipt_card_view);
            uploadButton = itemView.findViewById(R.id.button_upload);
            infoButton = itemView.findViewById(R.id.button_code_info);
        }
    }
}
