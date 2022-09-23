package com.brewingjava.mahavir.controllers.test;

import java.util.ArrayList;

public class CategoryResponse {
 
    private ArrayList<String> categories;

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public CategoryResponse() {
    }

    public CategoryResponse(ArrayList<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoryResponse [categories=" + categories + "]";
    }

    
}
