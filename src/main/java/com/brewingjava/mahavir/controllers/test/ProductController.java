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
    
    @PostMapping("/excel")
    public ResponseEntity<?> addExcelData(@RequestParam("file") MultipartFile file){
        try {
            if(excelHelper.checkFileType(file)){
                List<ProductDetail> products = excelHelper.convertExcelToListOfProductDetails(file.getInputStream());
                productDao.saveAll(products);

                // products = excelHelper.addSubCategories(file.getInputStream());
                // productDao.saveAll(products);
                // return ResponseEntity.ok(products);
                responseMessage.setMessage("Excel data added successfully");
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            }else{
                responseMessage.setMessage("File not acceptable");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/excel/factorsAffected")
    public ResponseEntity<?> addFactorsAffected(@RequestParam("file") MultipartFile file){
        try {
            boolean flag = excelHelper.addFactorsAffected(file.getInputStream());
            if(flag){
                responseMessage.setMessage("Product variants uploaded successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            else{
                responseMessage.setMessage("File not acceptable");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}

