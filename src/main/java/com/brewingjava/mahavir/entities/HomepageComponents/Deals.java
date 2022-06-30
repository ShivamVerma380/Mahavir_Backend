package com.brewingjava.mahavir.entities.HomepageComponents;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "deals")
@Component
public class Deals {

      //on categoryName  click subCategories + subSubCategories filters should come
      //Deals of the Day, Top Picks = title
      //Model numbers
      //Categories={Mobiles,TV,...} based on  model numbers category

    @Id
    public String title;

    public HashSet<String> modelNumbers;

    public ArrayList<String> categories;

    public Deals() {
    }

    public Deals(String title, HashSet<String> modelNumbers, ArrayList<String> categories) {
        this.title = title;
        this.modelNumbers = modelNumbers;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashSet<String> getModelNumbers() {
        return modelNumbers;
    }

    public void setModelNumbers(HashSet<String> modelNumbers) {
        this.modelNumbers = modelNumbers;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Deals [categories=" + categories + ", modelNumbers=" + modelNumbers + ", title=" + title + "]";
    }

  


    
    
}
