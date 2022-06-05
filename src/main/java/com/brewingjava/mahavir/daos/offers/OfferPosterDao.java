package com.brewingjava.mahavir.daos.offers;

import com.brewingjava.mahavir.entities.offers.OfferPosters;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferPosterDao extends MongoRepository<OfferPosters,String>{
    
    public List<OfferPosters> findOffersByCategory(String category);
}
