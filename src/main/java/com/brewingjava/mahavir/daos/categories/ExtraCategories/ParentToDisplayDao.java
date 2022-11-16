package com.brewingjava.mahavir.daos.categories.ExtraCategories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.categories.ExtraCategories.Parent;

@Component
public interface ParentToDisplayDao extends MongoRepository<Parent, String> {

    public Parent getParentByParentName(String parentName);

}
    
