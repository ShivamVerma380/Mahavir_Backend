package com.brewingjava.mahavir.daos.HomepageComponents;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.HomepageComponents.ShopByBrands;


public interface ShopByBrandsDao extends MongoRepository<ShopByBrands,String>{
    
    public ShopByBrands findBybrandName(String  brandName);
}
