package com.brewingjava.mahavir.controllers;

import com.brewingjava.mahavir.entities.ProductDetail;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductDetailsController {

    @Autowired
    ResponseMessage responseMessage;
    //public ResponseEntity<?> addProductDetails(@RequestBody ProductDetail productsDetails){ {
        // try{

        // }
        // catch(Exception e){
        //     e.printStackTrace();
        //     responseMessage.setMessage(e.getMessage());
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        // }
    //}
    }

