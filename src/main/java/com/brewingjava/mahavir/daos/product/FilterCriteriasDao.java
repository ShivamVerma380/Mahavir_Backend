package com.brewingjava.mahavir.daos.product;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.product.FilterCriterias;

public interface FilterCriteriasDao extends MongoRepository<FilterCriterias, String> {

    public FilterCriterias getFilterCriteriasBycategory(String category);
}
    
