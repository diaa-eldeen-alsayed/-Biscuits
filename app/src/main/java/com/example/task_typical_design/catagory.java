package com.example.task_typical_design;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class catagory {

    @SerializedName("name")
    private String name ;
    @SerializedName("items")
    private ArrayList<items> products;

    public ArrayList<items> getItems() {
        return products;
    }

    public String getName() {
        return name;
    }



}
