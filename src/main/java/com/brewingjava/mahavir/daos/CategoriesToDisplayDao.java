package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.CategoriesToDisplay;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesToDisplayDao extends MongoRepository<CategoriesToDisplay,String>{

    public CategoriesToDisplay findBycategory(String category);
    
}
