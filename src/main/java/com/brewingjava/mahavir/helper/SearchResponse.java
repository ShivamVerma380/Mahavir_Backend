package com.brewingjava.mahavir.helper;

import java.util.ArrayList;

public class SearchResponse {

    public String id;

    public String name;

    public String price;

    public String highlights;

    public String category;

    public SearchResponse() {
    }

    public SearchResponse(String id, String name) {
        this.id = id;
        this.name = name;
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

    
    

    

    public String gethightlights() {
        return highlights;
    }

    public void sethightlights(String hightlights) {
        this.highlights = hightlights;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SearchResponse [hightlights=" + highlights + ", id=" + id + ", name=" + name + ", price=" + price
                + "]";
    }

    

    

    
    

    


    

    

    
    
    

}
