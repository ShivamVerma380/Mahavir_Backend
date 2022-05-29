package com.brewingjava.mahavir.daos.categories;

import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesToDisplayDao extends MongoRepository<CategoriesToDisplay,String>{

    public CategoriesToDisplay findBycategory(String category);
    
}
