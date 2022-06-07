package com.brewingjava.mahavir.controllers.product;

import com.brewingjava.mahavir.entities.categories.ProductInformationItem;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.product.ProductDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestParam("productPrice") String productPrice ,@RequestParam("offerPrice") String offerPrice,@RequestParam("category") String category,@RequestParam("subCategory") String subCategory,@RequestParam("subSubCategory") String subSubCategory ,@RequestParam("Items") ArrayList<String> items, @RequestHeader("Authorization") String authorization){
        try {
            return productDetailsService.addProductDetail(modelNumber,productName, productDescription, productPrice,offerPrice, productImage1, productImage2, productImage3, productImage4, productImage5, category, subCategory, subSubCategory,items, authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-product-information/{ModelNumber}")
    public ResponseEntity<?> addProductDetails(@RequestHeader("Authorization") String authorization,@PathVariable("ModelNumber")String modelNumber, @RequestBody ArrayList<HashMap<String,String>> subItems){
        try {

            
                // System.out.println(productDetail.getitemName());
                // System.out.println(productDetail.getSubitemNames());
                //List<HashMap<String,String>> list = productDetail.get(i).getSubitemNames();
                
            
            return productDetailsService.addProductInformationByModelNumber(authorization, modelNumber, subItems);

            //return productDetailsService.addProductInformationByModelNumber(authorization, modelNumber, productDetail);
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

    @GetMapping("/get-reviews/{modelNumber}")
    public ResponseEntity<?> getReviews(@PathVariable("modelNumber") String modelNumber){
        try {
            return productDetailsService.getReviewsByModelNumber(modelNumber);
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

    @GetMapping("/get-products/{modelNumber}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getProductByModelNumber(@PathVariable("modelNumber") String modelNumber){
        try {
            return productDetailsService.getProductByModelNumber(modelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


}

