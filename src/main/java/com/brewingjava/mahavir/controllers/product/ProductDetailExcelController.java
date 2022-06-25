package com.brewingjava.mahavir.controllers.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.product.ProductDetailsServiceExcel;

@RestController
@CrossOrigin(origins = "*")
public class ProductDetailExcelController {
    @Autowired
    public ProductDetailsServiceExcel productDetailsServiceExcel;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public JwtUtil jwtUtil;

    // @PostMapping("/add-product/excel")
    // public ResponseEntity<?> addProductFromExcel(@RequestHeader("Authorization")String authorization, @RequestParam("Products") MultipartFile multipartFile){
    //     try {
    //         String token = authorization.substring(7);
    //         String email = jwtUtil.extractUsername(token);
    //         admin = adminDao.findByEmail(email);
    //         if(admin==null){
    //             responseMessage.setMessage("Only admin can add products");
    //             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
    //         }
    //         return productDetailsServiceExcel.save(multipartFile);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         responseMessage.setMessage(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    //     }
    // }

}
