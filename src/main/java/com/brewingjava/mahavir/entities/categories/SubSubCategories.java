package com.brewingjava.mahavir.entities.categories;

import java.util.HashSet;

public class SubSubCategories {

    public String subSubCategoryName;
    
    public HashSet<String> productName;
    
    public SubSubCategories() {
    }

    public SubSubCategories(String subSubCategoryName, HashSet<String> productName) {
        this.subSubCategoryName = subSubCategoryName;
        this.productName = productName;
    }

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }

    public void setSubSubCategoryName(String subSubCategoryName) {
        this.subSubCategoryName = subSubCategoryName;
    }

    public HashSet<String> getProductName() {
        return productName;
    }

    public void setProductName(HashSet<String> productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "SubSubCategories [productName=" + productName + ", subSubCategoryName=" + subSubCategoryName + "]";
    }

    
    
    

    
}
