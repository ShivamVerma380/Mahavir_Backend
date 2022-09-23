package com.brewingjava.mahavir.entities.product;

import org.bson.types.Binary;

public class Factors {  // add-productvariant/modelNumber/factor i.e. 3GB (Non images data) Key Value title,subtitle ,price
                        //add-productvariantimages/modelNumber/factor i.e. 3GB(images data) Key value productImage 1,2,3,4,5 => MultipartFIle
    private String factorname;  //title 

    //Color title price images

    private String factorValueNonImg;
    
    private String factorValueImg;


    // Add Product Information dynamically based on factors  List<Hashmaps>


    public Factors() {

    }


    public Factors(String factorname, String factorValueNonImg, String factorValueImg) {
        this.factorname = factorname;
        this.factorValueNonImg = factorValueNonImg;
        this.factorValueImg = factorValueImg;
    }


    public Factors(String factorname, String factorValueImg) {
        this.factorname = factorname;
        this.factorValueImg = factorValueImg;
    }


    public String getFactorname() {
        return factorname;
    }


    public void setFactorname(String factorname) {
        this.factorname = factorname;
    }


    public String getFactorValueNonImg() {
        return factorValueNonImg;
    }


    public void setFactorValueNonImg(String factorValueNonImg) {
        this.factorValueNonImg = factorValueNonImg;
    }


    public String getFactorValueImg() {
        return factorValueImg;
    }


    public void setFactorValueImg(String factorValueImg) {
        this.factorValueImg = factorValueImg;
    }


    @Override
    public String toString() {
        return "Factors [factorValueImg=" + factorValueImg + ", factorValueNonImg=" + factorValueNonImg
                + ", factorname=" + factorname + "]";
    }

    
    
    
    
}
