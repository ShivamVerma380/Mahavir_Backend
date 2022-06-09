package com.brewingjava.mahavir.services.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.Map.Entry;

import org.apache.catalina.connector.Response;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.product.Factors;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.product.ProductVariants;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Repository
@Component
public class ProductVariantService {

    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    public ResponseEntity<?> addProductVariantFactor(String authorization, String factorName, String modelNumber) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin == null) {
                responseMessage.setMessage("Only admin can add this product variant");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail == null) {
                responseMessage.setMessage("No product found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<ProductVariants> list = productDetail.getProductVariants();
            ProductVariants productVariants = new ProductVariants();
            productVariants.setFactorName(factorName);
            productVariants.setFactorsAffected(new ArrayList<>());
            list.add(productVariants);
            productDetail.setProductVariants(list);
            productDetailsDao.save(productDetail);
            responseMessage.setMessage("Product Variant Factor Name added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addNonImgFactorsAffected(String authorization, String factorName, String modelNumber,
            HashMap<String, String> nonImgFactors) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin == null) {
                responseMessage.setMessage("Only admin can add this product variant");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail == null) {
                responseMessage.setMessage("No product found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<ProductVariants> list = productDetail.getProductVariants();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFactorName().equals(factorName)) {
                    List<Factors> factorsAffected = list.get(i).getFactorsAffected();

                    for (String key : nonImgFactors.keySet()) {
                        Factors factors = new Factors();
                        factors.setFactorname(key);
                        factors.setFactorValueNonImg(nonImgFactors.get(key));
                        factorsAffected.add(factors);
                    }
                    list.get(i).setFactorsAffected(factorsAffected);
                    productDetail.setProductVariants(list);
                    productDetailsDao.save(productDetail);
                    responseMessage.setMessage("Non Img Variant Factors added Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
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

    public ResponseEntity<?> addImgFactorsAffected(String authorization, String factorName, String modelNumber,
            MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4,
            MultipartFile image5) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin == null) {
                responseMessage.setMessage("Only admin can add this product variant");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail == null) {
                responseMessage.setMessage("No product found!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<ProductVariants> list = productDetail.getProductVariants();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFactorName().equals(factorName)) {
                    List<Factors> factorsAffected = list.get(i).getFactorsAffected();

                    
                    Factors factors = new Factors("productImage1",new Binary(BsonBinarySubType.BINARY,image1.getBytes()));
                    factorsAffected.add(factors);
                    factors = new Factors("productImage2",new Binary(BsonBinarySubType.BINARY,image2.getBytes()));
                    factorsAffected.add(factors);
                    factors = new Factors("productImage3",new Binary(BsonBinarySubType.BINARY,image3.getBytes()));
                    factorsAffected.add(factors);
                    factors = new Factors("productImage4",new Binary(BsonBinarySubType.BINARY,image4.getBytes()));
                    factorsAffected.add(factors);
                    factors = new Factors("productImage5",new Binary(BsonBinarySubType.BINARY,image5.getBytes()));
                    factorsAffected.add(factors);                   
                    

                    list.get(i).setFactorsAffected(factorsAffected);
                    productDetail.setProductVariants(list);
                    productDetailsDao.save(productDetail);
                    responseMessage.setMessage("Img Variant Factors added Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
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

    
}
