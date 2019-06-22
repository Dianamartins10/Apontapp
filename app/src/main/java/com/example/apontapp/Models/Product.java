package com.example.apontapp.Models;

public class Product {
    String name;
    String category;
    String user_id;

    public Product(String user_id, String name, String category) {
        this.user_id= user_id;
        this.name = name;
        this.category = category;
    }



    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
