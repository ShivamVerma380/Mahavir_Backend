package com.brewingjava.mahavir.controllers.categories;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.categories.CategoriesToDisplayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class CategoriesToDisplayController {
    
    @Autowired
    public ResponseMessage responseMessage;


    @Autowired
    public CategoriesToDisplayService categoriesToDisplayService;

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestHeader("Authorization")String authorization,@RequestParam("CategoryName")String category,@RequestParam("CategoryImage")MultipartFile multipartFile){
        try{
            return categoriesToDisplayService.addCategory(authorization, category,multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-sub-category")
    public ResponseEntity<?> addSubCategory(@RequestHeader("Authorization") String authorization,@RequestParam("CategoryName")String category,@RequestParam("SubCategoryName")String subCategory){
        try {

            return categoriesToDisplayService.addSubCategory(authorization, category, subCategory);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-sub-sub-category")
    public ResponseEntity<?> addSubSubCategory(@RequestHeader("Authorization")String authorization,@RequestParam("CategoryName")String category,@RequestParam("SubCategoryName")String subCategoryName,@RequestParam("SubSubCategoryName")String subSubCategory){
        try {
            return categoriesToDisplayService.addSubSubCategory(authorization, category, subCategoryName, subSubCategory);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories(){
        try {
            return categoriesToDisplayService.getCategories();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    

    @GetMapping("/get-sub-categories")
    public ResponseEntity<?> getSubCategories(@RequestHeader("Authorization") String authorization,@RequestParam("Category")String category){
        try {
            return categoriesToDisplayService.getSubCategories(authorization, category);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-sub-sub-categories")
    public ResponseEntity<?> getSubSubCategories(@RequestHeader("Authorization")String authorization,@RequestParam("Category")String category,@RequestParam("SubCategory")String subCategory){
        try {
            return categoriesToDisplayService.getSubSubCategories(authorization, category, subCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }   
    }
    
}
