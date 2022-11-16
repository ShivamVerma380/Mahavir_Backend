package com.brewingjava.mahavir.entities.categories.ExtraCategories;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;

@Document(collection = "extra_categories")
public class Parent {

    @Id
    private String parentName;

    private ArrayList<CategoriesToDisplay> categories;

    public Parent() {
    }

    public Parent(String parentName, ArrayList<CategoriesToDisplay> categories) {
        this.parentName = parentName;
        this.categories = categories;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<CategoriesToDisplay> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoriesToDisplay> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Parent [parentName=" + parentName + ", categories=" + categories + "]";
    }

    


    
}
