package com.brewingjava.mahavir.helper;

import java.util.ArrayList;

public class SubCategoriesAdminResponse {
    

    public String subCategoryName;

    public ArrayList<String> subSubCategories;

    public SubCategoriesAdminResponse() {
    }

    public SubCategoriesAdminResponse(String subCategoryName, ArrayList<String> subSubCategories) {
        this.subCategoryName = subCategoryName;
        this.subSubCategories = subSubCategories;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public ArrayList<String> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(ArrayList<String> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

    @Override
    public String toString() {
        return "SubCategoriesAdminResponse [subCategoryName=" + subCategoryName + ", subSubCategories="
                + subSubCategories + "]";
    }

    
}
