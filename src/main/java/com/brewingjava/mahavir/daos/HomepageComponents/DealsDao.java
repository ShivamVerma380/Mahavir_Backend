package com.brewingjava.mahavir.daos.HomepageComponents;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.HomepageComponents.Deals;

public interface DealsDao extends MongoRepository<Deals, String> {

    public Deals getDealsBytitle(String title);

}
