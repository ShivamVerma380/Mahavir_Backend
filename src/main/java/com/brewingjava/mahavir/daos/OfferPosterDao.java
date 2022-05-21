package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.OfferPosters;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferPosterDao extends MongoRepository<OfferPosters,String>{
    
}
