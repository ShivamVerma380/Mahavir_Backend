package com.brewingjava.mahavir.entities;

import java.util.List;

import org.springframework.data.annotation.Id;

public class SubCategories {
    
    private String subCategoryName;

    private List<String> subSubCategories;

    public SubCategories() {
    }

    public SubCategories(String subCategoryName, List<String> subSubCategories) {
        this.subCategoryName = subCategoryName;
        this.subSubCategories = subSubCategories;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public List<String> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(List<String> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

    @Override
    public String toString() {
        return "SubCategories [subCategoryName=" + subCategoryName + ", subSubCategories=" + subSubCategories + "]";
    }

}
