package com.brewingjava.mahavir.entities.offers.HybridPosters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "posters_title")
public class PostersTitle {
    
    @Id
    public String title;

    public HashSet<String> ModelNos; //To store direct modelNos without image...

    public ArrayList<ImageOffer> imageModelNos;  // To store multiple images n in one image multiple modelnos...

    public PostersTitle() {
    }

    public PostersTitle(String title, HashSet<String> modelNos, ArrayList<ImageOffer> imageModelNos) {
        this.title = title;
        ModelNos = modelNos;
        this.imageModelNos = imageModelNos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashSet<String> getModelNos() {
        return ModelNos;
    }

    public void setModelNos(HashSet<String> modelNos) {
        ModelNos = modelNos;
    }

    public ArrayList<ImageOffer> getImageModelNos() {
        return imageModelNos;
    }

    public void setImageModelNos(ArrayList<ImageOffer> imageModelNos) {
        this.imageModelNos = imageModelNos;
    }

    @Override
    public String toString() {
        return "PostersTitle [ModelNos=" + ModelNos + ", imageModelNos=" + imageModelNos + ", title=" + title + "]";
    }


    
    
    
}
