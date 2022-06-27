package com.brewingjava.mahavir.helper;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResponse {

    public String id;

    public String name;

    public String price;

    public String highlights;

    public String category;

    public String subCategory = "BRAND";

    public String subSubCategory;

    public SearchResponse() {
    }

    

    public SearchResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }



    public SearchResponse(String id, String name, String price, String highlights, String category, String subCategory,
            String subSubCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.highlights = highlights;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(String subSubCategory) {
        this.subSubCategory = subSubCategory;
    }

    @Override
    public String toString() {
        return "SearchResponse [category=" + category + ", highlights=" + highlights + ", id=" + id + ", name=" + name
                + ", price=" + price + ", subCategory=" + subCategory + ", subSubCategory=" + subSubCategory + "]";
    }
}
