package com.brewingjava.mahavir.entities.product;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class ProductDescription {
    
    private String title;

    private String description;

    private String image;

    public ProductDescription(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public ProductDescription() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductDescription [description=" + description + ", image=" + image + ", title=" + title + "]";
    }


    
    
}
