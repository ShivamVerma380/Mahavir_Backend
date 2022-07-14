package com.brewingjava.mahavir.controllers.test;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.HomepageComponents.DealsDao;
import com.brewingjava.mahavir.daos.HomepageComponents.ShopByBrandsDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.ExcelHelper;
import com.brewingjava.mahavir.helper.ResponseMessage;

@RestController
@Component

public class ProductController {

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public ExcelHelper excelHelper;

    @Autowired
    public ProductDetailsDao productDao;
    


    @PostMapping("/excel/products")
    public ResponseEntity<?> addProducts(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addProducts(file.getInputStream());
        } catch (Exception e) {
            // e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    
    @PostMapping("/excel/Categories")
    public ResponseEntity<?> addCategories(@RequestParam("file") MultipartFile file){
        try {
            String message = excelHelper.addCategories(file.getInputStream());
            responseMessage.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    @PostMapping("/excel/filters")
    public ResponseEntity<?> addFilterCriterias(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addFilterCriterias(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @PostMapping("/excel/offerposters")
    public ResponseEntity<?> addOfferPosters(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addOfferPosters(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/excel/shopByBrands")
    public ResponseEntity<?> addBrands(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addBrands(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    public ShopByBrandsDao shopByBrandsDao;

    @GetMapping("/excel/shopByBrands")
    public ResponseEntity<?> getShopByBrands(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(shopByBrandsDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    // @GetMapping("/excel")
    // public ResponseEntity<?> getProducts(){
    //     try {
    //         return ResponseEntity.status(HttpStatus.OK).body(productDao.findAll());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         responseMessage.setMessage(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    //     }
    // }

    @PostMapping("/excel/deals")
    public ResponseEntity<?> addDeals(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addDeals(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    public DealsDao dealsDao;

    @GetMapping("/excel/deals")
    public ResponseEntity<?> getDeals(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(dealsDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}

