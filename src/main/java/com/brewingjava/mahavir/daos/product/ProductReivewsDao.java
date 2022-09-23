package com.brewingjava.mahavir.daos.product;

import com.brewingjava.mahavir.entities.product.ProductReviews;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductReivewsDao extends MongoRepository<ProductReviews, String> {
    public ProductReviews findProductReviewsBymodelNumber(String modelNumber);

}
    

