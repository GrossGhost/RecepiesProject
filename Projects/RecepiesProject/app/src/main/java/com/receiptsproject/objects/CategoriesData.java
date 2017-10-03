package com.receiptsproject.objects;

import io.realm.RealmObject;


public class CategoriesData extends RealmObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
