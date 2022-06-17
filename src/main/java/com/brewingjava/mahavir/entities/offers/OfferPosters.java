package com.brewingjava.mahavir.entities.offers;

import java.util.HashMap;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection="offer_posters")
public class OfferPosters {

    private Binary image;

    private List<String> modelNumbers;

    public String isMegaPoster;
    
    public String category;  //Display mobile offers on mobile click

    public OfferPosters() {
    }

    public OfferPosters(Binary image, List<String> modelNumbers,
            String category) {
        this.image = image;
        this.modelNumbers = modelNumbers;
        this.category = category;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public List<String> getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(List<String> modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getIsMegaPoster() {
        return isMegaPoster;
    }

    public void setIsMegaPoster(String isMegaPoster) {
        this.isMegaPoster = isMegaPoster;
    }

    @Override
    public String toString() {
        return "OfferPosters [category=" + category + ", image=" + image + ", isMegaPoster=" + isMegaPoster
                + ", modelNumbers=" + modelNumbers + "]";
    }   
    
    
}
