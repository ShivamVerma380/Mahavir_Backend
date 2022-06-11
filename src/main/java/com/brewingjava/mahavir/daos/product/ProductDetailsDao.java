package com.brewingjava.mahavir.daos.product;

import com.brewingjava.mahavir.entities.product.ProductDetail;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDetailsDao extends MongoRepository<ProductDetail, String> {
    public ProductDetail findProductDetailBymodelNumber(String modelNumber);

    public List<ProductDetail> findProductDetailsBycategory(String category);

}
    

