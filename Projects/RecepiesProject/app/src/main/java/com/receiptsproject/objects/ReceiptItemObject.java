package com.receiptsproject.objects;

import android.net.Uri;

import io.realm.RealmObject;

public class ReceiptItemObject extends RealmObject{
    private String title;
    private String category;
    private Uri image;

    public ReceiptItemObject(String title, String category, Uri image) {
        this.title = title;
        this.category = category;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
