package com.brewingjava.mahavir.entities.offers;

import java.util.HashMap;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection="offer_posters")
public class OfferPosters {

    private Binary image;

    private HashMap<String,OfferPosters> offerProductsList;

    public HashMap<String,OfferTypeDetails> offerDetails;

    public OfferPosters() {
    }

    public OfferPosters(Binary image, HashMap<String, OfferPosters> offerProductsList) {
        this.image = image;
        this.offerProductsList = offerProductsList;
    }

    public HashMap<String, OfferPosters> getOfferProductsList() {
        return offerProductsList;
    }

   
    public void setOfferProductsList(HashMap<String, OfferPosters> offerProductsList) {
        this.offerProductsList = offerProductsList;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }


    public HashMap<String, OfferTypeDetails> getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(HashMap<String, OfferTypeDetails> offerDetails) {
        this.offerDetails = offerDetails;
    }

    @Override
    public String toString() {
        return "OfferPosters [image=" + image + ", offerProductsList=" + offerProductsList + "]";
    }   
    
}
