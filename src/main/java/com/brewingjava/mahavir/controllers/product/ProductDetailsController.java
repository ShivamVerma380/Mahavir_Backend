package com.brewingjava.mahavir.controllers.product;

import com.brewingjava.mahavir.entities.categories.ProductInformationItem;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.product.ProductDetailsService;
import com.brewingjava.mahavir.services.product.ProductVariantService;

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
@CrossOrigin(origins = "*")
public class ProductDetailsController {

    @Autowired
    ResponseMessage responseMessage;

    @Autowired
    public ProductDetailsService productDetailsService;

    @Autowired
    public ProductVariantService productVariantService;
    
    @PostMapping("/add-product")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addProductDetails(@RequestParam("modelNumber") String modelNumber,@RequestParam("productName") String productName, @RequestParam("productHighlights") String productHighlights,@RequestParam("productImage1") String productImage1,
    @RequestParam("productImage2")String productImage2,@RequestParam("productImage3") String productImage3,@RequestParam("productImage4") String productImage4,@RequestParam("productImage5") String productImage5,
    @RequestParam("productPrice") String productPrice ,@RequestParam("offerPrice") String offerPrice,@RequestParam("category") String category, @RequestHeader("Authorization") String authorization){
        try{
            return productDetailsService.addProductDetail(modelNumber,productName, productHighlights, productPrice,offerPrice, productImage1, productImage2, productImage3, productImage4, productImage5, category, authorization);
        }catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-product-sub-categories/{modelNumber}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addProductSubCategories(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber,@RequestBody HashMap<String,String> subCategoriesMap){
        try {
            return productDetailsService.addProductSubCategories(authorization, modelNumber, subCategoriesMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }   

    @PostMapping("/add-product-information/{ModelNumber}")
    public ResponseEntity<?> addProductDetails(@RequestHeader("Authorization") String authorization,@PathVariable("ModelNumber")String modelNumber, @RequestBody HashMap<String,HashMap<String,String>> subItems){
        try {
            return productDetailsService.addProductInformationByModelNumber(authorization, modelNumber, subItems);
        }catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-product-variants/{ModelNumber}")
    public ResponseEntity<?> addProductVariants(@RequestHeader("Authorization") String authorization,@PathVariable("ModelNumber")String modelNumber,@RequestBody HashMap<String,ArrayList<String>> variants){
        try {
            return productDetailsService.addProductVariants(authorization,modelNumber,variants);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-review/{modelNumber}")
    public ResponseEntity<?> addReview(@PathVariable("modelNumber") String modelNumber, @RequestHeader("Authorization") String authorization ,@RequestParam("Review")String review,@RequestParam("Rating") String rating,@RequestParam("Date") String date){
        long R = Long.parseLong(rating); 
        try {
            return productDetailsService.addReview(authorization,modelNumber,R, review, date);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-product-variant-factor/{ModelNumber}")
    public ResponseEntity<?> addProductVariantFactor(@RequestHeader("Authorization")String authorization,@RequestParam("FactorName") String factorName,@PathVariable("ModelNumber")String ModelNumber){
        try {
            return productVariantService.addProductVariantFactor(authorization, factorName, ModelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    //ModelNumber,FactorName,  keytoChange => non img values
    @PostMapping("/add-product-variant-factor/{ModelNumber}/{FactorName}")
    public ResponseEntity<?> addNonImgFactorsAffected(@RequestHeader("Authorization")String authorization,@PathVariable("FactorName") String factorName,@PathVariable("ModelNumber")String modelNumber,@RequestBody HashMap<String,String> nonImgFactors){
        try {

            return productVariantService.addNonImgFactorsAffected(authorization, factorName, modelNumber, nonImgFactors);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-product-variant-factor/image/{ModelNumber}/{FactorName}")
    public ResponseEntity<?> addImgFactorsAffected(@RequestHeader("Authorization")String authorization,@PathVariable("FactorName") String factorName,@PathVariable("ModelNumber")String modelNumber,@RequestParam("ProductImage1") String image1,@RequestParam("ProductImage2") String image2,@RequestParam("ProductImage3") String image3,@RequestParam("ProductImage4") String image4,@RequestParam("ProductImage5") String image5){
        try {

            return productVariantService.addImgFactorsAffected(authorization, factorName, modelNumber, image1,image2,image3,image4,image5);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/filter-criterias/{modelNumber}")
    public ResponseEntity<?> addProductFilterCriterias(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber,@RequestBody HashMap<String,String> filterCriterias){
        try {
            return productDetailsService.addProductFilterCriterias(authorization, modelNumber, filterCriterias);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    

    @PostMapping("/free-item/{modelNumber}")
    public ResponseEntity<?> addFreeItem(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber, @RequestParam("name") String name,@RequestParam("price") String price , @RequestParam("image") String image){
        try {
            return productDetailsService.addFreeItem(authorization, modelNumber, name, price, image);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }   

    @PostMapping("/description/{modelNumber}")
    public ResponseEntity<?> addDescription(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber,@RequestParam("title") String title, @RequestParam("description") String description,@RequestParam("image") String image){
        try {
            return productDetailsService.addDescription(authorization, modelNumber, title, description, image);
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
     

    @DeleteMapping("/remove-product/{modelNumber}")
    public ResponseEntity<?> removeProductDetails(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber){
        try {
            return productDetailsService.removeProductDetails(authorization, modelNumber);
            
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

    @GetMapping("/products/{Category}")
    public ResponseEntity<?> getProductsCategory(@PathVariable("Category") String category){
        try {
            return productDetailsService.getProductByCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-search-products")
    public ResponseEntity<?> getSearchProducts(){
        try {
            return productDetailsService.getSearchProducts();
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

    @GetMapping("/get-products-by-subCategory/{Category}/{SubCategory}/{SubSubCategory}")
    public ResponseEntity<?> getProductBySubCategory(@PathVariable("Category") String category,@PathVariable("SubCategory")String subCategory,@PathVariable("SubSubCategory") String subSubCategory){
        try {
            return productDetailsService.getProductsBySubCategory(category, subCategory, subSubCategory);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-products/{modelNumber}/{factorName}")
    public ResponseEntity<?> getProductDetailsByFactor(@PathVariable("modelNumber")String modelNumber,@PathVariable("factorName")String factorName){
        try {
            return productDetailsService.getProductDetailsByFactors(modelNumber, factorName);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }

    @GetMapping("/get-products-by-category/{Category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable("Category") String category){
        try {
            return productDetailsService.getProductByCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/similar-products/{modelNumber}/{SubsubCategory}/{SubCategory}/{Category}")
    public ResponseEntity<?> getSimilarProducts(@PathVariable("modelNumber") String modelNumber,@PathVariable("SubsubCategory") String subSubCategory,@PathVariable("SubCategory") String subCategory,@PathVariable("Category") String category){
        try {
            return productDetailsService.getSimilarProducts(modelNumber, subSubCategory, subCategory, category);
        } catch (Exception e) {
            e.printStackTrace();
           responseMessage.setMessage(e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage); 
        }
    }


}

