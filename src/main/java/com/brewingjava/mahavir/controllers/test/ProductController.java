package com.brewingjava.mahavir.controllers.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.HomepageComponents.DealsDao;
import com.brewingjava.mahavir.daos.HomepageComponents.ShopByBrandsDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.categories.ExtraCategories.ParentToDisplayDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.HomepageComponents.BrandCategory;
import com.brewingjava.mahavir.entities.HomepageComponents.ShopByBrands;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
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
    

    @GetMapping("/")
    public ResponseEntity<?> getWelcome(){
        try {
            responseMessage.setMessage("Welcome");
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


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

    @GetMapping("/excel/shopByBrands/{brand}/{category}")
    public ResponseEntity<?> getShopByBrandsCategory(@PathVariable("brand") String brand,@PathVariable("category") String category){

        try {
            ShopByBrands brands = shopByBrandsDao.findBybrandName(brand);
            if(brands==null){
                responseMessage.setMessage("Brand not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }   
            ArrayList<BrandCategory> list = brands.getBrandCategories();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getCategory().equals(category)){
                    return ResponseEntity.status(HttpStatus.OK).body(list.get(i).getProducts());
                }
            }
            responseMessage.setMessage("Category not found");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
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

    @Autowired
    public CategoriesToDisplayDao categoriesToDisplayDao;


    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){
        try {
            List<CategoriesToDisplay> list = categoriesToDisplayDao.findAll();
            ArrayList<String> categoryName = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                categoryName.add(list.get(i).getCategory());
            }
            CategoryResponse obj = new CategoryResponse(categoryName);
            return ResponseEntity.status(HttpStatus.OK).body(obj);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/excel/isCategoryInNavbar")
    public ResponseEntity<?> addIsInNavbar(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addCategoryIsInNavBar(file.getInputStream());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @PostMapping("/excel/parentCategories")
    public ResponseEntity<?> addParentCategories(@RequestParam("file") MultipartFile file){
        try {
            return excelHelper.addParentCategories(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    public ParentToDisplayDao parentCategoriesDao;

    @GetMapping("/extraCategories")
    public ResponseEntity<?> getParentCategories(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parentCategoriesDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}

