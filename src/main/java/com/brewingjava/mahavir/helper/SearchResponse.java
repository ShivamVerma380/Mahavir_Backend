package com.brewingjava.mahavir.helper;

public class SearchResponse {

    public String modelNumber;

    public String productName;

    public SearchResponse() {
    }

    

    public SearchResponse(String modelNumber, String productName) {
        this.modelNumber = modelNumber;
        this.productName = productName;
    }



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    

    public String getModelNumber() {
        return modelNumber;
    }



    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }



    @Override
    public String toString() {
        return "SearchResponse [modelNumber=" + modelNumber + ", productName=" + productName + "]";
    }


    

    

    
    
    

}
