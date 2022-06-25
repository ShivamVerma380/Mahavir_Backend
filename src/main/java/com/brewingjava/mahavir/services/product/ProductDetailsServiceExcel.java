package com.brewingjava.mahavir.services.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.ExcelHelper;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Repository
@Component
public class ProductDetailsServiceExcel {

    @Autowired
    public ExcelHelper excelHelper;
    
    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public ResponseMessage responseMessage;

    
    // public ResponseEntity<?> save(MultipartFile multipartFile){
    //     try {
    //         List<ProductDetail> list =  excelHelper.convertExcelToListOfProductDetails(multipartFile.getInputStream());
    //         this.productDetailsDao.saveAll(list);
    //         responseMessage.setMessage("Products saved successfully");
    //         return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         responseMessage.setMessage(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    //     }
    // }


    
}
