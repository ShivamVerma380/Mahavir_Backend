package com.brewingjava.mahavir.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "categories_to_display")
public class CategoriesToDisplay {
    
    @Id
    private String category;

    public CategoriesToDisplay() {
    }

    public CategoriesToDisplay(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoriesToDisplay [category=" + category + "]";
    }
    
}
