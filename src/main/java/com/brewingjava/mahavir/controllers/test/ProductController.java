package com.brewingjava.mahavir.controllers.test;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.test.ProductDao;
import com.brewingjava.mahavir.entities.test.ProductDetail;
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
    public ProductDao productDao;
    
    @PostMapping("/excel")
    public ResponseEntity<?> addExcelData(@RequestParam("file") MultipartFile file){
        try {
            if(excelHelper.checkFileType(file)){
                List<ProductDetail> products = excelHelper.convertExcelToListOfProductDetails(file.getInputStream());
                productDao.saveAll(products);
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

}

