package com.receiptsproject.objects;

import android.net.Uri;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;

public class ReceiptItemObject extends RealmObject{
    private String title;
    private String category;
    private String image;
    private String shorterLink;

    public ReceiptItemObject() {

    }

    public ReceiptItemObject(String title, String category, String image) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.shorterLink = "Not Shared";
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

    public String getShorterLink() {
        return shorterLink;
    }

    public void setShorterLink(String shorterLink) {
        this.shorterLink = shorterLink;
    }

    public String getShorterId() {

        if (shorterLink.length() > 15)
            return this.shorterLink.substring(15);
        else
            return this.shorterLink;
    }

}
