package com.brewingjava.mahavir.services.product;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.brewingjava.mahavir.entities.product.Factors;
import com.brewingjava.mahavir.entities.product.FreeItem;
import com.brewingjava.mahavir.entities.product.ProductDescription;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.product.ProductReviews;
import com.brewingjava.mahavir.entities.product.ProductVariants;
import com.brewingjava.mahavir.entities.product.Review;
import com.brewingjava.mahavir.entities.user.Orders;
import com.brewingjava.mahavir.entities.user.UserRequest;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ProductsDetailsResponse;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.helper.SearchResponse;

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

    public ResponseEntity<?> addProductDetail( String modelNumber,String productName, String productHighlights,
            String productPrice,String offerPrice, String productImage1, String productImage2, String productImage3,
            String productImage4, String productImage5, String category,
            String authorization) {
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
            productDetail.setProductHighlights(productHighlights);
            productDetail.setProductImage1(productImage1);
            productDetail.setProductImage2(productImage2);
            productDetail.setProductImage3(productImage3);
            productDetail.setProductImage4(productImage4);
            productDetail.setProductImage5(productImage5);
            
            productDetail.setProductPrice(productPrice);
            productDetail.setCategory(category);
            productDetail.setProductName(productName);
            productDetail.setOfferPrice(offerPrice);
            
            productDetail.setProductInformation(new HashMap<>());
            productDetail.setSubCategoryMap(new HashMap<String,String>());
            productDetail.setProductVariants(new ArrayList<>());
            productDetail.setVariants(new HashMap<>());
            productDetail.setFiltercriterias(new HashMap<>());
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

    public ResponseEntity<?> addProductVariants(String auth,String modelNumber,HashMap<String,ArrayList<String>> variants){
        try {
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add product variants");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("Product Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            productDetail.setVariants(variants);
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Product Variants added Successfully");
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

    public ResponseEntity<?> addReview(String auth,String modelNumber,long rating,String review,String date){
        try {
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<Orders> userOrders = userRequest.getProductsBoughtByUser();
            boolean flag = false;
            for(int i=0;i<userOrders.size();i++){
                if(userOrders.get(i).getModelNumber().equals(modelNumber)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                responseMessage.setMessage("You have not bought products");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            
            ProductReviews productReviews = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
            if(productReviews==null){
                ProductReviews new_productReview = new ProductReviews();
                new_productReview.setAverageRatings(0);
                new_productReview.setModelNumber(modelNumber);
                new_productReview.setNosOfFiveStars(0);
                new_productReview.setNosOfFourStars(0);
                new_productReview.setNosOfOneStars(0);
                new_productReview.setNosOfThreeStars(0);
                new_productReview.setNosOfTwoStars(0);
                new_productReview.setTotalRatings(0);
                new_productReview.setTotalReviews(0);
                new_productReview.setReviews(new ArrayList<>());
                Review reviewObj = new Review();
                reviewObj.setDate(date);
                reviewObj.setRating(rating);
                reviewObj.setReviewer_name(userRequest.getFirstName()+" "+userRequest.getLastName());
                reviewObj.setReview(review);
                List<Review> reviews = new_productReview.getReviews();
                reviews.add(reviewObj);
                new_productReview.setReviews(reviews);
                if(rating==1){
                    new_productReview.setNosOfOneStars(new_productReview.getNosOfOneStars()+1);

                }else if(rating==2){
                    new_productReview.setNosOfTwoStars(new_productReview.getNosOfTwoStars()+1);

                }else if(rating==3){
                    new_productReview.setNosOfThreeStars(new_productReview.getNosOfThreeStars()+1);

                }else if(rating==4){
                    new_productReview.setNosOfFourStars(new_productReview.getNosOfFourStars()+1);

                }else if(rating==5) {
                    new_productReview.setNosOfFiveStars(new_productReview.getNosOfFiveStars()+1);
                }
                new_productReview.setTotalRatings(new_productReview.getTotalRatings()+1);
                new_productReview.setTotalReviews(new_productReview.getTotalReviews()+1);
                double avg = (1*new_productReview.getNosOfOneStars()+ 2*new_productReview.getNosOfTwoStars()+3*new_productReview.getNosOfThreeStars()+4*new_productReview.getNosOfFourStars()+5*new_productReview.getNosOfFiveStars())/new_productReview.getTotalRatings();
                new_productReview.setAverageRatings(avg);
                productReviewsDao.save(new_productReview);
                responseMessage.setMessage("Product Review added successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            Review reviewObj = new Review();
            reviewObj.setDate(date);
            reviewObj.setRating(rating);
            reviewObj.setReviewer_name(userRequest.getFirstName()+" "+userRequest.getLastName());
            reviewObj.setReview(review);
            List<Review> reviews = productReviews.getReviews();
            reviews.add(reviewObj);
            productReviews.setReviews(reviews);
            if(rating==1){
                productReviews.setNosOfOneStars(productReviews.getNosOfOneStars()+1);

            }else if(rating==2){
                productReviews.setNosOfTwoStars(productReviews.getNosOfTwoStars()+1);

            }else if(rating==3){
                productReviews.setNosOfThreeStars(productReviews.getNosOfThreeStars()+1);

            }else if(rating==4){
                productReviews.setNosOfFourStars(productReviews.getNosOfFourStars()+1);

            }else if(rating==5) {
                productReviews.setNosOfFiveStars(productReviews.getNosOfFiveStars()+1);
            }
            productReviews.setTotalRatings(productReviews.getTotalRatings()+1);
            double avg = (1*productReviews.getNosOfOneStars()+ 2*productReviews.getNosOfTwoStars()+3*productReviews.getNosOfThreeStars()+4*productReviews.getNosOfFourStars()+5*productReviews.getNosOfFiveStars())/(double)(productReviews.getTotalRatings());
            productReviews.setAverageRatings(avg);
            productReviews.setTotalReviews(productReviews.getTotalReviews()+1);
            productReviewsDao.save(productReviews);
            responseMessage.setMessage("Product Review added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addFreeItem(String authorization,String modelNumber,String name,String price,String image){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can add free item");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);    
            if(productDetail==null){
                responseMessage.setMessage("Product Not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            FreeItem freeItem = new FreeItem(name,price,new Binary(BsonBinarySubType.BINARY,image.getBytes()));
            productDetail.setFreeItem(freeItem);
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Free Item added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addDescription(String authorization,String modelNumber,String title,String description,String image){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can add free item");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("Product Not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ArrayList<ProductDescription> list = productDetail.getProductDescriptions();
            if(list==null){
                list = new ArrayList<>();
            }
            ProductDescription productDescription = new ProductDescription(title, description,image);
            list.add(productDescription);
            productDetail.setProductDescriptions(list);
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Product Details updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> getProductDetailsByFactors(String modelNumber,String factorName){
        try {
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("Product Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<ProductVariants> list = productDetail.getProductVariants();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getFactorName().equals(factorName)){
                    List<Factors> factors = list.get(i).getFactorsAffected();
                    for(int j=0;j<factors.size();j++){
                        if(factors.get(j).getFactorname().equals("productName"))
                            productDetail.setProductName(factors.get(j).getFactorValueNonImg());
                        else if(factors.get(j).getFactorname().equals("productHighlights"))
                            productDetail.setProductHighlights(factors.get(j).getFactorValueNonImg());
                        else if(factors.get(j).getFactorname().equals("productImage1"))
                            productDetail.setProductImage1(factors.get(j).getFactorValueImg());
                        else if(factors.get(j).getFactorname().equals("productImage2"))
                            productDetail.setProductImage2(factors.get(j).getFactorValueImg());
                        else if(factors.get(j).getFactorname().equals("productImage3"))
                            productDetail.setProductImage3(factors.get(j).getFactorValueImg());
                        else if(factors.get(j).getFactorname().equals("productImage4"))
                            productDetail.setProductImage4(factors.get(j).getFactorValueImg());
                        else if(factors.get(j).getFactorname().equals("productImage5"))
                            productDetail.setProductImage5(factors.get(j).getFactorValueImg());
                        else if(factors.get(j).getFactorname().equals("productPrice"))
                            productDetail.setProductPrice(factors.get(j).getFactorValueNonImg());
                        else if(factors.get(j).getFactorname().equals("OfferPrice"))
                            productDetail.setOfferPrice(factors.get(j).getFactorValueNonImg());
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(productDetail);
                }
            }
            responseMessage.setMessage("Factor Not Found");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }    

/*
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
    */

    public ResponseEntity<?> getReviewsByModelNumber(String modelNumber) {
        try {
            ProductReviews reviews = productReviewsDao.findProductReviewsBymodelNumber(modelNumber);
            if(reviews==null){
                ProductReviews review = new ProductReviews();
                review.setReviews(new ArrayList<>());
                return ResponseEntity.status(HttpStatus.OK).body(review);
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reviews);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductDetail> productDetails = productDetailsDao.findAll();
            List<ProductsDetailsResponse> productsDetailsResponses = new ArrayList<>();
            for(int i = 0; i < productDetails.size(); i++){
                ProductsDetailsResponse productsDetailsResponse = new ProductsDetailsResponse();
                productsDetailsResponse.setModelNumber(productDetails.get(i).getModelNumber());
                productsDetailsResponse.setProductName(productDetails.get(i).getProductName());
                productsDetailsResponse.setOfferPrice(productDetails.get(i).getOfferPrice());
                productsDetailsResponse.setProductPrice(productDetails.get(i).getProductPrice());
                productsDetailsResponse.setProductImage1(productDetails.get(i).getProductImage1());
                productsDetailsResponse.setProductHighlights(productDetails.get(i).getProductHighlights());
                productsDetailsResponse.setSubCategoryMap(productDetails.get(i).getSubCategoryMap());
                productsDetailsResponse.setCategory(productDetails.get(i).getCategory());
                productsDetailsResponses.add(productsDetailsResponse);
            }
            return ResponseEntity.status(HttpStatus.OK).body(productsDetailsResponses);
            // return ResponseEntity.status(HttpStatus.OK).body(productDetailsDao.findAll());
        } catch (Exception e) {

            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);

        }
    }

    public ResponseEntity<?> getSearchProducts(){
        try {
            List<ProductDetail> productDetails = productDetailsDao.findAll();
            List<SearchResponse> list = new ArrayList<>();
            
            List<CategoriesToDisplay> existingCategoriesToDisplay = categoriesToDisplayDao.findAll();
            //Add all categories
            for(int i=0;i<existingCategoriesToDisplay.size();i++){
                SearchResponse searchResponse = new SearchResponse(existingCategoriesToDisplay.get(i).getCategory(),existingCategoriesToDisplay.get(i).getCategory());
                searchResponse.setType("category");
                searchResponse.setCategory(existingCategoriesToDisplay.get(i).getCategory());
                searchResponse.setModelNumbers(new ArrayList<>());        
                list.add(searchResponse);
            }
            //Add all SubSubCategories
            for(int i=0;i<existingCategoriesToDisplay.size();i++){
                List<SubCategories> existingSubCategories = existingCategoriesToDisplay.get(i).getSubCategories();
                for(int j=0;j<existingSubCategories.size();j++){
                    List<SubSubCategories> existingSubSubCategories = existingSubCategories.get(j).getSubSubCategories();
                    for(int k=0;k<existingSubSubCategories.size();k++){
                        String name = existingSubSubCategories.get(k).getSubSubCategoryName()+" in  "+existingCategoriesToDisplay.get(i).getCategory();
                        SearchResponse searchResponse = new SearchResponse(name,name);
                        searchResponse.setType("subSubCategory");
                        searchResponse.setCategory(existingCategoriesToDisplay.get(i).getCategory());
                        searchResponse.setSubSubCategory(existingSubSubCategories.get(k).getSubSubCategoryName());
                        HashSet<String> modelNos = existingSubSubCategories.get(k).getmodelNumber();
                        
                        searchResponse.setModelNumbers(new ArrayList<>(modelNos));
                        list.add(searchResponse);
                    }
                }
            }

            for(int i=0;i<productDetails.size();i++){
                SearchResponse searchResponse = new SearchResponse(productDetails.get(i).getModelNumber(),productDetails.get(i).getProductName());
                searchResponse.setCategory(productDetails.get(i).getCategory());
                String[] highlights = productDetails.get(i).getProductHighlights().split(";");
                String str="";
                for(int j=0;j<highlights.length;j++){
                    str+=highlights[j]+" ";
                }
                searchResponse.setHighlights(str);
                searchResponse.setPrice(productDetails.get(i).getProductPrice());
                HashMap<String,String> subCategoryMap = productDetails.get(i).getSubCategoryMap();
                searchResponse.setSubSubCategory(subCategoryMap.get("BRAND"));
                ArrayList<String> modelNumbers = new ArrayList<>();
                modelNumbers.add(productDetails.get(i).getModelNumber());
                searchResponse.setModelNumbers(modelNumbers);
                searchResponse.setType("product");
                list.add(searchResponse);
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getProductsByCategory(String category){
        try {
            CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(existingCategoriesToDisplay==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
            List<SubCategories> subCategories = existingCategoriesToDisplay.getSubCategories();
            List<ProductsDetailsResponse> list = new ArrayList<>();
            for(int i=0;i<subCategories.size();i++){
                List<SubSubCategories> subSubCategories = subCategories.get(i).getSubSubCategories();
                for(int j=0;j<subSubCategories.size();j++){
                    HashSet<String> modelNums = subSubCategories.get(j).getmodelNumber();
                    for(String mNo:modelNums){
                        ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(mNo);
                        ProductsDetailsResponse productsDetailsResponse = new ProductsDetailsResponse();
                        productsDetailsResponse.setModelNumber(productDetail.getModelNumber());
                        productsDetailsResponse.setProductName(productDetail.getProductName());
                        productsDetailsResponse.setOfferPrice(productDetail.getOfferPrice());
                        productsDetailsResponse.setProductPrice(productDetail.getProductPrice());
                        productsDetailsResponse.setProductImage1(productDetail.getProductImage1());
                        productsDetailsResponse.setProductHighlights(productDetail.getProductHighlights());
                        productsDetailsResponse.setSubCategoryMap(productDetail.getSubCategoryMap());
                        productsDetailsResponse.setCategory(productDetail.getCategory());
                        list.add(productsDetailsResponse);
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);

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
                //1. get ProductFilters from category Name.
                //2. Iterate Hashmap "Operating System":{"OS":"IOS"}
                //3. Check OS key in productfilters if not add it.
                //4. Also add IOS in HashSet<String>
                String categoryName = existingProductDetail.getCategory();
                CategoriesToDisplay category = categoriesToDisplayDao.findBycategory(categoryName);
                HashMap<String,HashSet<String>> filters = category.getProductFilters();
                if(filters==null){
                    HashMap<String,HashSet<String>> hMap = new HashMap<String,HashSet<String>>();
                    for(String key:productInformation.keySet()){
                        HashMap<String,String> hm = productInformation.get(key);
                        for(String feature:hm.keySet()){
                            HashSet<String> values=new HashSet<String>();
                            values.add(hm.get(feature));
                            hMap.put(feature, values);
                        }
                    }
                    category.setProductFilters(hMap);
                    categoriesToDisplayDao.save(category);
                    productDetailsDao.save(existingProductDetail);
                    responseMessage.setMessage("Product Information Saved Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }
                for(String key:productInformation.keySet()){
                    HashMap<String,String> hm = productInformation.get(key);
                    for(String feature:hm.keySet()){
                        if(filters.containsKey(feature)){
                            // HashSet<String> values = filters.get(feature);
                            HashSet<String> values = filters.get(feature);
                            values.add(hm.get(feature));
                            // filters.(feature,values);
                            filters.put(feature, values);
                        }
                        else{
                            HashSet<String> values=new HashSet<String>();
                            values.add(hm.get(feature));
                            filters.put(feature, values);
                        }
                    }
                }
                category.setProductFilters(filters);
                categoriesToDisplayDao.save(category);
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

    public ResponseEntity<?> getProductByCategory(String category){
        try {
            CategoriesToDisplay categoriesToDisplay = categoriesToDisplayDao.findBycategory(category);

            if(categoriesToDisplay==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            List<ProductDetail> productDetails = productDetailsDao.findProductDetailsBycategory(category);
            // if(productDetails==null || productDetails.isEmpty()){
            //     responseMessage.setMessage("Empty");
            //     return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            // }
            for(int i=0;i<productDetails.size();i++){
                System.out.println(productDetails.get(i).getModelNumber());
            }
            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addProductFilterCriterias(String authorization, String modelNumber,
            HashMap<String, String> filterCriterias) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin == null) {
                responseMessage.setMessage("Only admin can add product information");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            if (filterCriterias == null) {
                responseMessage.setMessage("Product Information is Empty");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail existingProductDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (existingProductDetail == null) {
                responseMessage.setMessage("Product Not Found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            existingProductDetail.setFiltercriterias(filterCriterias);
            productDetailsDao.save(existingProductDetail);
            responseMessage.setMessage("Product Information Saved Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            // existingProductDetail.setProductInformation(productDetail);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
        }
    }

    public ResponseEntity<?> getSimilarProducts(String modelNumber,String subSubCategory,String subCategory,String Category){
        try {
            List<ProductsDetailsResponse> list = new ArrayList<>();
            CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(Category);
            if(existingCategoriesToDisplay==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<SubCategories> subCategories = existingCategoriesToDisplay.getSubCategories();
            boolean flag = true;
            List<SubSubCategories> subSubCategories = new ArrayList<>();
            for(int i=0;i<subCategories.size();i++){
                if(subCategories.get(i).getSubCategoryName().equals(subCategory)){
                    subSubCategories = subCategories.get(i).getSubSubCategories();
                    for(int j=0;j<subSubCategories.size();j++){
                        if(subSubCategories.get(j).getSubSubCategoryName().equals(subSubCategory)){
                            HashSet<String> modelNos = subSubCategories.get(j).getmodelNumber();
                            modelNos.remove(modelNumber);
                            for(String modelNo:modelNos){
                                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNo);
                                ProductsDetailsResponse productsDetailsResponse = new ProductsDetailsResponse();
                                productsDetailsResponse.setModelNumber(productDetail.getModelNumber());
                                productsDetailsResponse.setProductName(productDetail.getProductName());
                                productsDetailsResponse.setOfferPrice(productDetail.getOfferPrice());
                                productsDetailsResponse.setProductHighlights(productDetail.getProductHighlights());
                                productsDetailsResponse.setProductImage1(productDetail.getProductImage1());
                                productsDetailsResponse.setProductPrice(productDetail.getProductPrice());
                                productsDetailsResponse.setSubCategoryMap(productDetail.getSubCategoryMap());
                                productsDetailsResponse.setCategory(productDetail.getCategory());
                                list.add(productsDetailsResponse);
                            }
                            if(list.size()>18){
                                flag = false;
                                break;
                            }
                            subSubCategories.remove(subSubCategories.get(j));
                            break;
                        }
                    }
                }
            }
            if(flag){
                for(int i=0;i<subSubCategories.size();i++){
                    HashSet<String> modelNos = subSubCategories.get(i).getmodelNumber();
                    //modelNos.remove(modelNumber);
                    for(String modelNo:modelNos){
                        ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNo);
                        ProductsDetailsResponse productsDetailsResponse = new ProductsDetailsResponse();
                        productsDetailsResponse.setModelNumber(productDetail.getModelNumber());
                        productsDetailsResponse.setProductName(productDetail.getProductName());
                        productsDetailsResponse.setOfferPrice(productDetail.getOfferPrice());
                        productsDetailsResponse.setProductHighlights(productDetail.getProductHighlights());
                        productsDetailsResponse.setProductImage1(productDetail.getProductImage1());
                        productsDetailsResponse.setProductPrice(productDetail.getProductPrice());
                        productsDetailsResponse.setSubCategoryMap(productDetail.getSubCategoryMap());
                        list.add(productsDetailsResponse);
                    }
                    if(list.size()>=18){
                        // return ResponseEntity.ok(list);
                        break;
                    }    
                }
            }


            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
