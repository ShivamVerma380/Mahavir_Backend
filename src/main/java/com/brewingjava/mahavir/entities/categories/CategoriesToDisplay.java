package com.brewingjava.mahavir.entities.categories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Component
@Document(collection = "categories_to_display")
public class CategoriesToDisplay {
    
    @Id
    private String category;

    private String category_image_url;

    
    private List<SubCategories> subCategories;

    
    private List<ProductInformationItem> productInformationItemList;

    private HashMap<String,HashSet<String>> productFilters; //For storing filters

    public CategoriesToDisplay() {
    }

    public CategoriesToDisplay(String category, String category_image_url) {
        this.category = category;
        this.category_image_url = category_image_url;
    }

    

    

    public CategoriesToDisplay(String category, String category_image_url, List<SubCategories> subCategories,
            List<ProductInformationItem> productInformationItemList) {
        this.category = category;
        this.category_image_url = category_image_url;
        this.subCategories = subCategories;
        this.productInformationItemList = productInformationItemList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_image() {
        return category_image_url;
    }

    public void setCategory_image(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public List<SubCategories> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategories> subCategories) {
        this.subCategories = subCategories;
    }

    

    public List<ProductInformationItem> getProductInformationItemList() {
        return productInformationItemList;
    }

    public void setProductInformationItemList(List<ProductInformationItem> productInformationItemList) {
        this.productInformationItemList = productInformationItemList;
    }

    public HashMap<String, HashSet<String>> getProductFilters() {
        return productFilters;
    }

    public void setProductFilters(HashMap<String, HashSet<String>> productFilters) {
        this.productFilters = productFilters;
    }

    @Override
    public String toString() {
        return "CategoriesToDisplay [category=" + category + ", category_image_url=" + category_image_url + ", productFilters="
                + productFilters + ", productInformationItemList=" + productInformationItemList + ", subCategories="
                + subCategories + "]";
    }
    
}
