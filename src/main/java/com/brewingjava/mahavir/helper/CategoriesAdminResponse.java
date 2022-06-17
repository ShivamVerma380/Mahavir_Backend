package com.brewingjava.mahavir.helper;

import java.util.ArrayList;

public class CategoriesAdminResponse {
    
    public String categoryName;

    public ArrayList<SubCategoriesAdminResponse> subCategories;

    public CategoriesAdminResponse() {
    }

    public CategoriesAdminResponse(String categoryName, ArrayList<SubCategoriesAdminResponse> subCategories) {
        this.categoryName = categoryName;
        this.subCategories = subCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<SubCategoriesAdminResponse> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategoriesAdminResponse> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "CategoriesAdminResponse [categoryName=" + categoryName + ", subCategories=" + subCategories + "]";
    }

}
