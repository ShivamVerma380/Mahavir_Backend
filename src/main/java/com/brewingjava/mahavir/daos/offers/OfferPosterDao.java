package com.brewingjava.mahavir.daos.offers;

import com.brewingjava.mahavir.entities.offers.OfferPosters;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferPosterDao extends MongoRepository<OfferPosters,String>{
    
}
