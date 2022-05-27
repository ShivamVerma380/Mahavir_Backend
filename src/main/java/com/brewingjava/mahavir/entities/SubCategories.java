package com.brewingjava.mahavir.entities;

import java.util.HashSet;

public class SubCategories {
    
    private String subCategoryName;

    private HashSet<SubSubCategories> subSubCategories;

    public SubCategories() {
    }

    public SubCategories(String subCategoryName, HashSet<SubSubCategories> subSubCategories) {
        this.subCategoryName = subCategoryName;
        this.subSubCategories = subSubCategories;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public HashSet<SubSubCategories> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(HashSet<SubSubCategories> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

    @Override
    public String toString() {
        return "SubCategories [subCategoryName=" + subCategoryName + ", subSubCategories=" + subSubCategories + "]";
    }

   

    
}
