package com.brewingjava.mahavir.entities.offers.HybridPosters;

import java.util.ArrayList;

import org.bson.types.Binary;

public class ImageOffer {
    
    public Binary image;

    public ArrayList<String> modelNos;

    public ImageOffer() {
    }

    public ImageOffer(Binary image, ArrayList<String> modelNos) {
        this.image = image;
        this.modelNos = modelNos;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public ArrayList<String> getModelNos() {
        return modelNos;
    }

    public void setModelNos(ArrayList<String> modelNos) {
        this.modelNos = modelNos;
    }

    @Override
    public String toString() {
        return "ImageOffer [image=" + image + ", modelNos=" + modelNos + "]";
    }

    
    

}
