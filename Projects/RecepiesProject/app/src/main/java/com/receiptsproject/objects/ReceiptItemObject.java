package com.receiptsproject.objects;

import android.net.Uri;

import io.realm.RealmObject;

public class ReceiptItemObject extends RealmObject{
    private String title;
    private String category;
    private String image;
    private String shareLink;

    public ReceiptItemObject() {

    }

    public ReceiptItemObject(String title, String category, String image) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.shareLink = "Not Shared";
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

    public String getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image.toString();
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }
}
