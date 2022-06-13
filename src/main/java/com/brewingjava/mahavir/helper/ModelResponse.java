package com.brewingjava.mahavir.helper;

public class ModelResponse {
    private String modelNumber;
    private String modelName;

    public ModelResponse() {
    }


    public ModelResponse(String modelNumber, String modelName) {
        this.modelNumber = modelNumber;
        this.modelName = modelName;
    }


    public String getModelNumber() {
        return modelNumber;
    }


    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }


    public String getModelName() {
        return modelName;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    @Override
    public String toString() {
        return "ModelResponse [modelName=" + modelName + ", modelNumber=" + modelNumber + "]";
    }

    

    
    
}
