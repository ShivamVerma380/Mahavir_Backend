package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class BrandCategory {
    
    private String category;

    private Binary catImage;

    private ArrayList<String> modelNumbers;

    public BrandCategory() {
    }

    public BrandCategory(String category, Binary catImage, ArrayList<String> modelNumbers) {
        this.category = category;
        this.catImage = catImage;
        this.modelNumbers = modelNumbers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Binary getCatImage() {
        return catImage;
    }

    public void setCatImage(Binary catImage) {
        this.catImage = catImage;
    }

    public ArrayList<String> getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(ArrayList<String> modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    @Override
    public String toString() {
        return "BrandCategory [catImage=" + catImage + ", category=" + category + ", modelNumbers=" + modelNumbers
                + "]";
    }

    

    

}
