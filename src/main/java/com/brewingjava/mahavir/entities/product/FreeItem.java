package com.brewingjava.mahavir.entities.product;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class FreeItem {
    
    private String name;

    private String price;

    private Binary image;

    public FreeItem() {
    }

    public FreeItem(String name, String price, Binary image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "FreeItem [image=" + image + ", name=" + name + ", price=" + price + "]";
    }

    
}
