package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.product.ProductDetail;

@Component
public class BrandCategory {
    
    private String category;

    private String catImage;

    private ArrayList<ProductDetail> products;

    public BrandCategory() {
    }

    public BrandCategory(String category, String catImage, ArrayList<ProductDetail> products) {
        this.category = category;
        this.catImage = catImage;
        this.products = products;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public ArrayList<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductDetail> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "BrandCategory [catImage=" + catImage + ", category=" + category + ", products=" + products + "]";
    }

}
