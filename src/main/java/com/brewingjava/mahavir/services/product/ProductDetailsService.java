package com.brewingjava.mahavir.services.product;

import java.util.ArrayList;
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
import com.brewingjava.mahavir.entities.categories.ProductInformationItem;
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
import org.springframework.security.access.method.P;
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
            String productPrice,String offerPrice, MultipartFile productImage1, MultipartFile productImage2, MultipartFile productImage3,
            MultipartFile productImage4, MultipartFile productImage5, String category,
            ArrayList<String> items ,String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add the product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
            if(existingCategory==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            }
            ProductDetail productDetail = new ProductDetail();
            productDetail.setModelNumber(modelNumber);
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
            
            productDetail.setProductPrice(productPrice);
            productDetail.setCategory(category);
            productDetail.setProductName(productName);
            productDetail.setOfferPrice(offerPrice);
            
            productDetail.setProductInformation(new HashMap<>());
            productDetail.setSubCategoryMap(new HashMap<String,String>());
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Model saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addProductSubCategories(String authorization,String modelNumber,HashMap<String,String> subCategoriesMap){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add the product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("Model Number not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }            

            String category = productDetail.getCategory();
            CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(existingCategoriesToDisplay==null){
                responseMessage.setMessage("Category Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<SubCategories> subCategories = existingCategoriesToDisplay.getSubCategories();
            HashMap<String,String> filteredSubCategoriesMap = new HashMap<>();
            for(int i=0;i<subCategories.size();i++){
                if(subCategoriesMap.containsKey(subCategories.get(i).getSubCategoryName())){
                    List<SubSubCategories> subSubCategories = subCategories.get(i).getSubSubCategories();
                    for(int j=0;j<subSubCategories.size();j++){
                        if(subCategoriesMap.get(subCategories.get(i).getSubCategoryName()).equals(subSubCategories.get(j).getSubSubCategoryName())){
                            filteredSubCategoriesMap.put(subCategories.get(i).getSubCategoryName(), subSubCategories.get(j).getSubSubCategoryName());
                            SubSubCategories updatedSubSubCategories = subSubCategories.get(j);
                            HashSet<String> updatedModelNumbers = updatedSubSubCategories.getmodelNumber();
                            updatedModelNumbers.add(modelNumber);
                            subSubCategories.get(j).setmodelNumber(updatedModelNumbers);
                            subCategories.get(i).setSubSubCategories(subSubCategories);
                            break;
                        }
                    }
                }
            }
            existingCategoriesToDisplay.setSubCategories(subCategories);
            categoriesToDisplayDao.save(existingCategoriesToDisplay);

            productDetail.setSubCategoryMap(filteredSubCategoriesMap);
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Product Details updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        
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
            if(admin==null){
                responseMessage.setMessage("Only admins can remove product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            CategoriesToDisplay categoriesToDisplay = categoriesToDisplayDao.findBycategory(productDetail.getCategory());
            if(categoriesToDisplay==null){
                responseMessage.setMessage("Sorry!! No Category Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            HashMap<String,String> subCategoriesMap = productDetail.getSubCategoryMap();
            
            List<SubCategories> subCategories = categoriesToDisplay.getSubCategories();
            
            for(int i=0;i<subCategories.size();i++){
                if(subCategoriesMap.containsKey(subCategories.get(i).getSubCategoryName())){
                    List<SubSubCategories> subSubCategories = subCategories.get(i).getSubSubCategories();
                    for(int j=0;j<subSubCategories.size();j++){
                        if(subCategoriesMap.get(subCategories.get(i).getSubCategoryName()).equals(subSubCategories.get(j).getSubSubCategoryName())){
                            SubSubCategories updatedSubSubCategories = subSubCategories.get(j);
                            HashSet<String> updatedModelNumbers = updatedSubSubCategories.getmodelNumber();
                            updatedModelNumbers.remove(modelNumber);
                            subSubCategories.get(j).setmodelNumber(updatedModelNumbers);
                            subCategories.get(i).setSubSubCategories(subSubCategories);
                            break;
                        }
                    }
                }
            }
            categoriesToDisplay.setSubCategories(subCategories);
            categoriesToDisplayDao.save(categoriesToDisplay);

            productDetailsDao.deleteById(modelNumber);

            responseMessage.setMessage("Product Removed Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getProductsBySubCategory(String category,String subCategory,String subSubCategory){
        try {
            CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(existingCategoriesToDisplay==null){
                responseMessage.setMessage("Category Not Found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<SubCategories> subCategories = existingCategoriesToDisplay.getSubCategories();
            
            for(int i=0;i<subCategories.size();i++){
                if(subCategories.get(i).getSubCategoryName().equals(subCategory)){
                    List<SubSubCategories> subSubCategories = subCategories.get(i).getSubSubCategories();
                    for(int j=0;j<subSubCategories.size();j++){
                        if(subSubCategories.get(j).getSubSubCategoryName().equals(subSubCategory)){
                            HashSet<String> modelNumbers = subSubCategories.get(j).getmodelNumber();
                            List<ProductDetail> productDetails = new ArrayList<>();
                            Iterator modelNumbersIterator = modelNumbers.iterator();
                            while(modelNumbersIterator.hasNext()){
                                try {
                                    ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber((String)modelNumbersIterator.next());
                                    productDetails.add(productDetail);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            responseMessage.setMessage("Products fetched successfully");
                            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
                        }
                    }
                    responseMessage.setMessage("SubSubCategory not found!!");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                }
            }
            responseMessage.setMessage("SubCategory Not Found");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);

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

            if (userRequest == null) {
                responseMessage.setMessage("User does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail == null) {
                responseMessage.setMessage("Product does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            // if(productReviewsDao.findProductReviewsBymodelNumber(modelNumber)!=null){
            // ProductReviews reviewsList =
            // productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
            // if(reviewsList.getReviews().get(reviewerName).equals(userDao.findByEmail(email).getFirstName())){
            // responseMessage.setMessage("You have already reviewed this product");
            // return
            // ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            // }
            // }
            List<Orders> orders = userRequest.getProductsBoughtByUser();
            if (orders == null) {
                responseMessage.setMessage("You have not bought this product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            String userName = userDao.findByEmail(email).getFirstName() + userDao.findByEmail(email).getLastName();

            // check if model number present in reviews
            // if present then add review hashmap to that model number
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getmodelNumber().equals(modelNumber)) {
                    ProductReviews productReviews = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
                    if (productReviews == null) {
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
                    } else {
                        // HashMap<String,String> pr = ;
                        if (productReviews.getReviews().containsKey(userName)) {
                            responseMessage.setMessage("You have already reviewed this product");
                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                        }
                        // for(int p = 0; p <
                        // productReviewsDao.findProductReviewsBymodelNumber(modelNumber).getReviews().size();
                        // p++){

                        // }
                        // if(pr.containsKey(userName)){
                        // responseMessage.setMessage("You have already reviewed");
                        // return
                        // ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                        // }
                        ProductReviews reviewsList = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
                        HashMap<String, Review> reviewerNameReview = reviewsList.getReviews();
                        // HashMap<String,String> newReviews = new HashMap<>();
                        // newReviews.put(userName, review);

                        // for(int j = 0; j < reviewsList.getReviews().size(); j++){
                        // System.out.println(reviewsList.getReviews());
                        // }
                        double pRating = 0.0;
                        Iterator hmIterator = reviewerNameReview.entrySet().iterator();
                        while (hmIterator.hasNext()) {
                            Map.Entry mapElement = (Map.Entry) hmIterator.next();
                            Review review1 = (Review) mapElement.getValue();
                            pRating = pRating + Double.parseDouble(review1.getRating());
                        }

                        String ProductRating = Double.toString(
                                (Double.parseDouble(rating) + pRating) / (reviewsList.getReviews().size() + 1));
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

    public ResponseEntity<?> getReviewsByModelNumber(String modelNumber) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productReviewsDao.findProductReviewsBymodelNumber(modelNumber));

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDetailsDao.findAll());
        } catch (Exception e) {

            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);

        }
    }

    public ResponseEntity<?> getProductByModelNumber(String modelNumber) {
        try {

            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail == null) {
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

    // Add Product Information By Model Number
    public ResponseEntity<?> addProductInformationByModelNumber(String authorization, String modelNumber,
            HashMap<String, HashMap<String,String>> productInformation) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin == null) {
                responseMessage.setMessage("Only admin can add product information");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            if (productInformation == null) {
                responseMessage.setMessage("Product Information is Empty");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail existingProductDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (existingProductDetail == null) {
                responseMessage.setMessage("Product Not Found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            // existingProductDetail.setProductInformation(productDetail);
            try {
                // existingProductDetail.setProductInformation(productDetail);
                //existingProductDetail.setSubItems(subItems);
                existingProductDetail.setProductInformation(productInformation);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            }

            productDetailsDao.save(existingProductDetail);
            responseMessage.setMessage("Product Information Saved Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
