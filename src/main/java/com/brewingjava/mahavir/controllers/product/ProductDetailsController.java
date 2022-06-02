package com.brewingjava.mahavir.controllers.product;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.product.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductDetailsController {

    @Autowired
    ResponseMessage responseMessage;

    @Autowired
    public ProductDetailsService productDetailsService;
    
    @PostMapping("/add-product")
    public ResponseEntity<?> addProductDetails(@RequestParam("modelNumber") String modelNumber,@RequestParam("productName") String productName, @RequestParam("productDesc") String productDescription,@RequestParam("productImage1") MultipartFile productImage1,
    @RequestParam("productImage2")MultipartFile productImage2,@RequestParam("productImage3") MultipartFile productImage3,@RequestParam("productImage4") MultipartFile productImage4,@RequestParam("productImage5") MultipartFile productImage5,
    @RequestParam("productPrice") String productPrice, @RequestParam("productVideo") MultipartFile productVideo ,@RequestParam("category") String category,@RequestParam("subCategory") String subCategory,@RequestParam("subSubCategory") String subSubCategory  ,@RequestHeader("Authorization") String authorization){
        try {
            return productDetailsService.addProductDetail(modelNumber,productName, productDescription, productPrice, productImage1, productImage2, productImage3, productImage4, productImage5, productVideo, category, subCategory, subSubCategory, authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestParam("modelNumber") String modelNumber,@RequestParam("rating")String rating, @RequestParam("review") String review, @RequestHeader("Authorization") String authorization){
        try {
            return productDetailsService.addReview(modelNumber,rating, review, authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @DeleteMapping("/remove-product")
    public ResponseEntity<?> removeProductDetails(@RequestHeader("Authorization") String authorization,@RequestParam("productName")String productName){
        try {
            return productDetailsService.removeProductDetails(authorization, productName);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getAllProducts(){
        try {
            return productDetailsService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }
}

