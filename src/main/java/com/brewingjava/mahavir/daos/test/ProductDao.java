package com.brewingjava.mahavir.daos.test;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.test.ProductDetail;


public interface ProductDao extends MongoRepository<ProductDetail,String>{

    public ProductDetail getProductDetailByModelNumber(String modelNumber);
    
}
