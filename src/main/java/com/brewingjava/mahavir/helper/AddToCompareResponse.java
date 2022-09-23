package com.brewingjava.mahavir.helper;

import java.util.ArrayList;
import java.util.HashMap;

public class AddToCompareResponse {
    
    public String subSubCategoryName;

    public ArrayList<ModelResponse> modelResponses = new ArrayList<>();

    public AddToCompareResponse() {
    }

    public AddToCompareResponse(String subSubCategoryName, ArrayList<ModelResponse> modelResponses) {
        this.subSubCategoryName = subSubCategoryName;
        this.modelResponses = modelResponses;
    }

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }

    public void setSubSubCategoryName(String subSubCategoryName) {
        this.subSubCategoryName = subSubCategoryName;
    }

    public ArrayList<ModelResponse> getModelResponses() {
        return modelResponses;
    }

    public void setModelResponses(ArrayList<ModelResponse> modelResponses) {
        this.modelResponses = modelResponses;
    }

    @Override
    public String toString() {
        return "AddToCompareResponse [modelResponses=" + modelResponses + ", subSubCategoryName=" + subSubCategoryName
                + "]";
    }

    
    
    
    

}
