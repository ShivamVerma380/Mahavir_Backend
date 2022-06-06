package com.brewingjava.mahavir.entities.categories;

import java.util.List;

public class ProductInformationItem {
    //This class will store dynamic product information for each Category.....

    public String itemName;  //General Feature
 
    public List<String> subitemNames;  // Brand ,Model Number, etc....

    public ProductInformationItem() {
    }

    public ProductInformationItem(String itemName, List<String> subitemNames) {
        this.itemName = itemName;
        this.subitemNames = subitemNames;
    }

    public String getitemName() {
        return itemName;
    }

    public void setitemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getSubitemNames() {
        return subitemNames;
    }

    public void setSubitemNames(List<String> subitemNames) {
        this.subitemNames = subitemNames;
    }

    @Override
    public String toString() {
        return "ProductInformationPart [itemName=" + itemName + ", subitemNames=" + subitemNames + "]";
    }
    
}
