package com.brewingjava.mahavir.entities.user;

public class AddToCompare {
    
    private String modelNumber;

    private String category;

    public AddToCompare() {
    }

    public AddToCompare(String modelNumber, String category) {
        this.modelNumber = modelNumber;
        this.category = category;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "AddToCompare [category=" + category + ", modelNumber=" + modelNumber + "]";
    }

    
}
