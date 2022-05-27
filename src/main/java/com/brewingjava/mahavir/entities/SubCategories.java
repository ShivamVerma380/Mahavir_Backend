package com.brewingjava.mahavir.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class SubCategories {
    
    private String subCategoryName;

    //private List<String> subSubCategories;
    Set<String> subSubCategories = new HashSet<>();

    public SubCategories() {
    }

    public SubCategories(String subCategoryName, Set<String> subSubCategories) {
        this.subCategoryName = subCategoryName;
        this.subSubCategories = subSubCategories;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Set<String> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(Set<String> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

    @Override
    public String toString() {
        return "SubCategories [subCategoryName=" + subCategoryName + ", subSubCategories=" + subSubCategories + "]";
    }

    
}
