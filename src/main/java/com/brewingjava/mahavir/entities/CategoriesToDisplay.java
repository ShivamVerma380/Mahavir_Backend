package com.brewingjava.mahavir.entities;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "categories_to_display")
public class CategoriesToDisplay {
    
    @Id
    private String category;

    private Binary category_image;

    public CategoriesToDisplay() {
    }

    public CategoriesToDisplay(String category, Binary category_image) {
        this.category = category;
        this.category_image = category_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Binary getCategory_image() {
        return category_image;
    }

    public void setCategory_image(Binary category_image) {
        this.category_image = category_image;
    }

    @Override
    public String toString() {
        return "CategoriesToDisplay [category=" + category + ", category_image=" + category_image + "]";
    }

    
    
    
}
