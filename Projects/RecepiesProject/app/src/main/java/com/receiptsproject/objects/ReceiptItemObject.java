package com.receiptsproject.objects;

public class ReceiptItemObject {
    private String title;
    private String category;
    private String image;

    public ReceiptItemObject(String title, String category, String image) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
