package com.brewingjava.mahavir.helper;

import java.util.HashMap;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class ProductsDetailsResponse {
    
    public String modelNumber;

    public String productId;

    private String productName;

    private String productHighlights;
    
    private String productImage1;

    private String OfferPrice;

    private String productPrice;

    private HashMap<String,String> subCategoryMap;

    private String category;

    private HashMap<String,String> filtercriterias;

    public ProductsDetailsResponse() {
    }


    


    public ProductsDetailsResponse(String modelNumber, String productName, String productHighlights,
            String productImage1, String offerPrice, String productPrice, HashMap<String, String> subCategoryMap,
            String category, HashMap<String, String> filtercriterias) {
        this.modelNumber = modelNumber;
        this.productName = productName;
        this.productHighlights = productHighlights;
        this.productImage1 = productImage1;
        OfferPrice = offerPrice;
        this.productPrice = productPrice;
        this.subCategoryMap = subCategoryMap;
        this.category = category;
        this.filtercriterias = filtercriterias;
    }





    public String getModelNumber() {
        return modelNumber;
    }


    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }


    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductHighlights() {
        return productHighlights;
    }


    public void setProductHighlights(String productHighlights) {
        this.productHighlights = productHighlights;
    }


    public String getProductImage1() {
        return productImage1;
    }


    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }


    public String getOfferPrice() {
        return OfferPrice;
    }


    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }


    public String getProductPrice() {
        return productPrice;
    }


    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public HashMap<String, String> getSubCategoryMap() {
        return subCategoryMap;
    }


    public void setSubCategoryMap(HashMap<String, String> subCategoryMap) {
        this.subCategoryMap = subCategoryMap;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, String> getFiltercriterias() {
        return filtercriterias;
    }

    public void setFiltercriterias(HashMap<String, String> filtercriterias) {
        this.filtercriterias = filtercriterias;
    }

    

    





    @Override
    public String toString() {
        return "ProductsDetailsResponse [OfferPrice=" + OfferPrice + ", category=" + category + ", filtercriterias="
                + filtercriterias + ", modelNumber=" + modelNumber + ", productHighlights=" + productHighlights
                + ", productId=" + productId + ", productImage1=" + productImage1 + ", productName=" + productName
                + ", productPrice=" + productPrice + ", subCategoryMap=" + subCategoryMap + "]";
    }





    public String getProductId() {
        return productId;
    }





    public void setProductId(String productId) {
        this.productId = productId;
    }    

}
