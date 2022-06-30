package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class BrandOfferPoster {
    
    private Binary offerPoster;

    private ArrayList<String> modelNumbers;

    private ArrayList<String> categories;

    public BrandOfferPoster() {
    }

    public BrandOfferPoster(Binary offerPoster, ArrayList<String> modelNumbers, ArrayList<String> categories) {
        this.offerPoster = offerPoster;
        this.modelNumbers = modelNumbers;
        this.categories = categories;
    }

    public Binary getOfferPoster() {
        return offerPoster;
    }

    public void setOfferPoster(Binary offerPoster) {
        this.offerPoster = offerPoster;
    }

    public ArrayList<String> getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(ArrayList<String> modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "BrandOfferPoster [categories=" + categories + ", modelNumbers=" + modelNumbers + ", offerPoster="
                + offerPoster + "]";
    }

    

    

}
