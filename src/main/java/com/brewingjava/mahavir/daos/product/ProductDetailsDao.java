package com.brewingjava.mahavir.daos.product;

import com.brewingjava.mahavir.entities.product.ProductDetail;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDetailsDao extends MongoRepository<ProductDetail, String> {
    public ProductDetail findProductDetailByproductName(String productName);
}
    

