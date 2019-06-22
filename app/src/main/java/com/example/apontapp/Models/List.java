package com.example.apontapp.Models;

import java.util.ArrayList;

public class List {
    String user_id;
    String listName;
    ArrayList<String> products;


    public List(String user_id, String listName, ArrayList<String> products) {
        this.user_id = user_id;
        this.listName = listName;
        this.products = products;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

}
