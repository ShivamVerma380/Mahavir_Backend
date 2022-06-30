package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class BrandCategory {
    
    private String category;

    private ArrayList<String> modelNumbers;

    public BrandCategory() {
    }

    public BrandCategory(String category, ArrayList<String> modelNumbers) {
        this.category = category;
        this.modelNumbers = modelNumbers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(ArrayList<String> modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    @Override
    public String toString() {
        return "BrandCategory [category=" + category + ", modelNumbers=" + modelNumbers + "]";
    }

    

}
