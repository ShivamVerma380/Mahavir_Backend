package com.brewingjava.mahavir.entities.categories;

import java.util.List;

public class SubCategories {
    
    private String subCategoryName;

    List<SubSubCategories> subSubCategories;

    public SubCategories() {
    }

    public SubCategories(String subCategoryName, List<SubSubCategories> subSubCategories) {
        this.subCategoryName = subCategoryName;
        this.subSubCategories = subSubCategories;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public List<SubSubCategories> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(List<SubSubCategories> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

    @Override
    public String toString() {
        return "SubCategories [subCategoryName=" + subCategoryName + ", subSubCategories=" + subSubCategories + "]";
    }

    
    
    

    
}
