package com.brewingjava.mahavir.entities.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "filter_criterias")
public class FilterCriterias {

    @Id
    public String category;

    public HashMap<String,ArrayList<String>> filterCriterias;

    public FilterCriterias() {
        this.filterCriterias = new HashMap<>();
    }

    

    public FilterCriterias(String category, HashMap<String, ArrayList<String>> filterCriterias) {
        this.category = category;
        this.filterCriterias = filterCriterias;
    }



    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, ArrayList<String>> getFilterCriterias() {
        return filterCriterias;
    }

    public void setFilterCriterias(HashMap<String, ArrayList<String>> filterCriterias) {
        this.filterCriterias = filterCriterias;
    }



    @Override
    public String toString() {
        return "FilterCriterias [category=" + category + ", filterCriterias=" + filterCriterias + "]";
    }

    

}
