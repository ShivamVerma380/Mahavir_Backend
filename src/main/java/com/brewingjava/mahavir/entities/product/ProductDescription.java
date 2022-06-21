package com.brewingjava.mahavir.entities.product;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class ProductDescription {
    
    private String title;

    private String description;

    private Binary image;

    public ProductDescription(String title, String description, Binary image) {
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

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductDescription [description=" + description + ", image=" + image + ", title=" + title + "]";
    }


    
    
}
