package com.brewingjava.mahavir.daos.offers.HybridPosters;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.offers.HybridPosters.PostersTitle;

public interface PostersTitleDao extends MongoRepository<PostersTitle,String> {

    public PostersTitle getPostersTitleBytitle(String title);
    
}
