package com.brewingjava.mahavir.services;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import com.brewingjava.mahavir.daos.AdminDao;
import com.brewingjava.mahavir.daos.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.ProductDetailsDao;
import com.brewingjava.mahavir.entities.Admin;
import com.brewingjava.mahavir.entities.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.ProductDetail;
import com.brewingjava.mahavir.entities.SubCategories;
import com.brewingjava.mahavir.entities.SubSubCategories;
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
    public ProductDetailsDao productDetailsDao;

    public ResponseEntity<?> addProductDetail(String modelNumber, String productName, String productDescription,
            String productPrice, MultipartFile productImage1, MultipartFile productImage2, MultipartFile productImage3,
            MultipartFile productImage4, MultipartFile productImage5, MultipartFile productVideo, String category,
            String subCategory, String subSubCategory, String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
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
                                    
                                    if (existingSubSubCategories.get(j).getModelNumber() == null) {
                                        HashSet<String> updatedModelNo = new HashSet<>();
                                        updatedModelNo.add(modelNumber);
                                        existingSubSubCategories.get(j).setModelNumber(updatedModelNo);
                                        existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                        existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                                        categoriesToDisplayDao.save(existingCategoriesToDisplay);

                                    } else {
                                        HashSet<String> model_no = existingSubSubCategories.get(j).getModelNumber();
                                        model_no.add(modelNumber);
                                        existingSubSubCategories.get(j).setModelNumber(model_no);
                                        existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                        existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                                        categoriesToDisplayDao.save(existingCategoriesToDisplay);
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
                                    productDetail.setProductVideo(
                                            new Binary(BsonBinarySubType.BINARY, productVideo.getBytes()));
                                    productDetail.setProductName(productName);
                                    productDetail.setProductPrice(productPrice);
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


    

}
