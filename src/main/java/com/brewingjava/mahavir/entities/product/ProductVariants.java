package com.brewingjava.mahavir.entities.product;

import java.util.List;

public class ProductVariants {

    private String FactorName; //3GB  input- Factor Name list (non images ya fir image)  

    private List<Factors> FactorsAffected;

    public ProductVariants() {

    }

    public ProductVariants(String factorName, List<Factors> factorsAffected) {
        FactorName = factorName;
        FactorsAffected = factorsAffected;
    }

    public String getFactorName() {
        return FactorName;
    }

    public void setFactorName(String factorName) {
        FactorName = factorName;
    }

    public List<Factors> getFactorsAffected() {
        return FactorsAffected;
    }

    public void setFactorsAffected(List<Factors> factorsAffected) {
        FactorsAffected = factorsAffected;
    }

    @Override
    public String toString() {
        return "ProductVariants [FactorName=" + FactorName + ", FactorsAffected=" + FactorsAffected + "]";
    }  

}
