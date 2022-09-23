package com.brewingjava.mahavir.entities.categories;

import java.util.HashSet;

public class SubSubCategories {

    public String subSubCategoryName;
    
    public HashSet<String> modelNumber;
    
    public SubSubCategories() {
    }

    public SubSubCategories(String subSubCategoryName, HashSet<String> modelNumber) {
        this.subSubCategoryName = subSubCategoryName;
        this.modelNumber = modelNumber;
    }

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }

    public void setSubSubCategoryName(String subSubCategoryName) {
        this.subSubCategoryName = subSubCategoryName;
    }

    public HashSet<String> getmodelNumber() {
        return modelNumber;
    }

    public void setmodelNumber(HashSet<String> modelNumber) {
        this.modelNumber = modelNumber;
    }

    @Override
    public String toString() {
        return "SubSubCategories [modelNumber=" + modelNumber + ", subSubCategoryName=" + subSubCategoryName + "]";
    }

    
    
    

    
}
