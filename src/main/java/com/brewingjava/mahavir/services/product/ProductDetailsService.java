package com.brewingjava.mahavir.services.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.daos.product.ProductReivewsDao;
import com.brewingjava.mahavir.daos.user.OrdersDao;
import com.brewingjava.mahavir.daos.user.UserDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.categories.SubCategories;
import com.brewingjava.mahavir.entities.categories.SubSubCategories;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.product.ProductReviews;
import com.brewingjava.mahavir.entities.product.Review;
import com.brewingjava.mahavir.entities.user.Orders;
import com.brewingjava.mahavir.entities.user.UserRequest;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
@Component
public class ProductDetailsService {

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public CategoriesToDisplayDao categoriesToDisplayDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public ProductReivewsDao productReviewsDao;

    @Autowired
    public Review review;
    public ResponseEntity<?> addProductDetail( String modelNumber,String productName, String productDescription,
            String productPrice, MultipartFile productImage1, MultipartFile productImage2, MultipartFile productImage3,
            MultipartFile productImage4, MultipartFile productImage5, MultipartFile productVideo, String category,
            String subCategory, String subSubCategory, String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            ProductDetail productDetail1 = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail1!=null){
                responseMessage.setMessage("Product already exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            if (admin != null) {
                // Find category that matches with input category
                CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
                if (existingCategoriesToDisplay != null) {
                    List<SubCategories> existingSubCategories = existingCategoriesToDisplay.getSubCategories();
                    // Find subCategory that matches with input subCategory
                    if (existingSubCategories == null) {
                        responseMessage.setMessage("Sorry!! No Sub Category found");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                    }
                    ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
                    int i = 0;
                    while (listIterator.hasNext()) {
                        if (listIterator.next().getSubCategoryName().equalsIgnoreCase(subCategory)) {
                            List<SubSubCategories> existingSubSubCategories = existingSubCategories.get(i)
                                    .getSubSubCategories();
                            // Check if existingSubSubCategories is null
                            if (existingSubSubCategories == null) {
                                responseMessage.setMessage("Sorry!!No Sub Sub Category found");
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                            }
                            // Now we have to check input SubSubCategories is present in list of Existing
                            // SubSubCategories or not;
                            ListIterator<SubSubCategories> existingSSCListIterator = existingSubSubCategories
                                    .listIterator();
                            int j = 0;
                            while (existingSSCListIterator.hasNext()) {
                                if (subSubCategory.equals(existingSSCListIterator.next().subSubCategoryName)) {
                                    // Now add model number here
                                    SubSubCategories subSubCategories = new SubSubCategories();
                                    subSubCategories.setSubSubCategoryName(subSubCategory);
                                    
                                    if (existingSubSubCategories.get(j).getmodelNumber() == null) {
                                        HashSet<String> updatedModelNo = new HashSet<>();
                                        updatedModelNo.add(modelNumber);
                                        existingSubSubCategories.get(j).setmodelNumber(updatedModelNo);
                                        existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                        existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                                        categoriesToDisplayDao.save(existingCategoriesToDisplay);

                                    } else {
                                        HashSet<String> product_name = existingSubSubCategories.get(j).getmodelNumber();
                                        if(product_name.contains(modelNumber)){
                                            responseMessage.setMessage("Model already exists");
                                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                                        }
                                        product_name.add(modelNumber);
                                        existingSubSubCategories.get(j).setmodelNumber(product_name);
                                        existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                        existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                                        categoriesToDisplayDao.save(existingCategoriesToDisplay);
                                    }

                                    ProductDetail productDetail = new ProductDetail();
                                    productDetail.setmodelNumber(modelNumber);
                                    productDetail.setProductDescription(productDescription);
                                    productDetail.setProductImage1(
                                            new Binary(BsonBinarySubType.BINARY, productImage1.getBytes()));
                                    productDetail.setProductImage2(
                                            new Binary(BsonBinarySubType.BINARY, productImage2.getBytes()));
                                    productDetail.setProductImage3(
                                            new Binary(BsonBinarySubType.BINARY, productImage3.getBytes()));
                                    productDetail.setProductImage4(
                                            new Binary(BsonBinarySubType.BINARY, productImage4.getBytes()));
                                    productDetail.setProductImage5(
                                            new Binary(BsonBinarySubType.BINARY, productImage5.getBytes()));
                                    productDetail.setProductVideo(
                                            new Binary(BsonBinarySubType.BINARY, productVideo.getBytes()));
                                    productDetail.setProductPrice(productPrice);
                                    productDetail.setCategory(category);
                                    productDetail.setSubCategory(subCategory);
                                    productDetail.setSubSubCategory(subSubCategory);
                                    productDetail.setProductName(productName);
                                    productDetail.setOfferPrice("0");
                                    productDetailsDao.save(productDetail);
                                    responseMessage.setMessage("Model saved successfully");
                                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

                                }
                                j++;
                            }
                            responseMessage.setMessage("Sorry!! No Sub Sub Category Found");
                            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                        }
                        i++;
                    }
                    // If not in if cond then out of while loop no sub category is found
                    responseMessage.setMessage("Sorry!!No Sub Category Found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);

                } else {
                    responseMessage.setMessage("Sorry!! Category Not Found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                }
            } else {
                responseMessage.setMessage("You do not have permission to add sub category");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> removeProductDetails(String authorization,String modelNumber){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin!=null){
                //Remove product from Product database
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
                if(productDetail!=null){
                    try {
                        productDetailsDao.delete(productDetail);
                        //Remove product Name from subSubCategory arraylist
                        String category = productDetail.getCategory();
                        String subCategory = productDetail.getSubCategory();
                        String subSubCategory = productDetail.getSubSubCategory();

                        CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
                        List<SubCategories> existingSubCategories = existingCategory.getSubCategories();
                        int i=0;
                        ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
                        if(listIterator==null){
                            responseMessage.setMessage("Product Not deleted");
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                        }
                        while(listIterator.hasNext()){
                            if(existingSubCategories.get(i).getSubCategoryName().equals(subCategory)){
                                List<SubSubCategories> existingSubSubCategories = existingSubCategories.get(i).getSubSubCategories();
                                ListIterator<SubSubCategories> ssCListIterator = existingSubSubCategories.listIterator();
                                int j=0;
                                while(ssCListIterator.hasNext()){
                                    if(existingSubSubCategories.get(j).getSubSubCategoryName().equals(subSubCategory)){
                                        System.out.println("Inside subSubCategories");
                                        HashSet<String> existingmodelNumber = existingSubSubCategories.get(j).getmodelNumber();
                                        existingmodelNumber.remove(modelNumber);
                                        System.out.println("Removed from hashset");
                                        // SubSubCategories new_SubSubCategories = new SubSubCategories();
                                        // new_SubSubCategories.setSubSubCategoryName(subSubCategory);
                                        // new_SubSubCategories.setmodelNumber(existingmodelNumber);
                                        
                                        existingSubSubCategories.get(j).setmodelNumber(existingmodelNumber);
                                        System.out.println("Product Name set");
                                        existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                        System.out.println("SubSub Categories set");
                                        existingCategory.setSubCategories(existingSubCategories);
                                        System.out.println("Categories set");
                                        categoriesToDisplayDao.save(existingCategory);
                                        System.out.println("Saved In Dao");
                                        responseMessage.setMessage("Product Removed successfully");
                                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                                    
                                    }
                                    j++;
                                }
                            }

                            i++;
                        }
                        responseMessage.setMessage("Product Not deleted");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseMessage.setMessage(e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
                    }
                                            

                }else{
                    responseMessage.setMessage("Product does not exist");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                }
                
                //Also remove from subSubCategory

            }else{
                responseMessage.setMessage("You do not have permission to remove a product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> addReview(String modelNumber, String rating, String review, String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);

            if(userRequest == null){
                responseMessage.setMessage("User does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail == null){
                responseMessage.setMessage("Product does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
            // if(productReviewsDao.findProductReviewsBymodelNumber(modelNumber)!=null){
            //     ProductReviews reviewsList = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
            //     if(reviewsList.getReviews().get(reviewerName).equals(userDao.findByEmail(email).getFirstName())){
            //         responseMessage.setMessage("You have already reviewed this product");
            //         return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            //     }
            // }
            List<Orders> orders = userRequest.getProductsBoughtByUser();
            if(orders==null){
                responseMessage.setMessage("You have not bought this product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            String userName = userDao.findByEmail(email).getFirstName() + userDao.findByEmail(email).getLastName();
                        
            // check if model number present in reviews
            // if present then add review hashmap to that model number
            for(int i= 0;i< orders.size();i++){
                if(orders.get(i).getmodelNumber().equals(modelNumber)){
                    ProductReviews productReviews = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
                    if(productReviews == null){
                        ProductReviews newProductReview = new ProductReviews();
                        newProductReview.setModelNumber(modelNumber); 
                        HashMap<String, Review> reviewerNameReview = new HashMap<>();
                        Review newReview = new Review();
                        newReview.setReview(review);
                        newReview.setRating(rating);
                        reviewerNameReview.put(userName, newReview);
                        newProductReview.setProductRating(rating);
                        newProductReview.setReviews(reviewerNameReview);
                        productReviewsDao.save(newProductReview);
                        responseMessage.setMessage("Review added successfully");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    }
                    else{
                        // HashMap<String,String> pr = ;
                        if(productReviews.getReviews().containsKey(userName)){
                            responseMessage.setMessage("You have already reviewed this product");
                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                        }
                        // for(int p = 0; p < productReviewsDao.findProductReviewsBymodelNumber(modelNumber).getReviews().size(); p++){
                            
                        // }
                        // if(pr.containsKey(userName)){
                        //     responseMessage.setMessage("You have already reviewed");
                        //     return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                        // }
                        ProductReviews reviewsList = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
                        HashMap<String, Review> reviewerNameReview = reviewsList.getReviews();
                        // HashMap<String,String> newReviews = new HashMap<>();
                        // newReviews.put(userName, review);
                        
                        // for(int j = 0; j < reviewsList.getReviews().size(); j++){
                        //     System.out.println(reviewsList.getReviews());
                        // }
                        double pRating = 0.0;
                        Iterator hmIterator = reviewerNameReview.entrySet().iterator();
                        while(hmIterator.hasNext()){
                            Map.Entry mapElement = (Map.Entry)hmIterator.next();
                            Review review1 = (Review)mapElement.getValue();
                            pRating = pRating + Double.parseDouble(review1.getRating());
                        }
                        
                        String ProductRating = Double.toString((Double.parseDouble(rating) + pRating)/ (reviewsList.getReviews().size()+1));
                        Review newReview = new Review();
                        newReview.setReview(review);
                        newReview.setRating(rating);
                        reviewerNameReview.put(userName, newReview);
                        reviewsList.setProductRating(ProductRating);
                        reviewsList.setReviews(reviewerNameReview);
                        productReviewsDao.save(reviewsList);
                        responseMessage.setMessage("Review added successfully");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                    }
                }
            }
            
           responseMessage.setMessage("Your order does not contain this product");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> getAllProducts(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDetailsDao.findAll());
        } catch (Exception e) {
            
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);

        }
    }

    
    public ResponseEntity<?> getProductByModelNumber(String authorization,String modelNumber){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            UserRequest userRequest = userDao.findByEmail(email);
            if(admin==null && userRequest==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("No Product found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            return ResponseEntity.status(HttpStatus.OK).body(productDetail);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    

    

}
