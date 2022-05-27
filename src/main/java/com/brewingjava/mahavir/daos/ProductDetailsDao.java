package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.ProductDetail;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDetailsDao extends MongoRepository<ProductDetail, String> {
    public ProductDetail findProductDetailByModelNumber(String modelNumber);
}
    

