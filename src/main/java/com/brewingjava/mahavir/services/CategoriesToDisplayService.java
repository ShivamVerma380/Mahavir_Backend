package com.brewingjava.mahavir.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.brewingjava.mahavir.daos.AdminDao;
import com.brewingjava.mahavir.daos.CategoriesToDisplayDao;
import com.brewingjava.mahavir.entities.Admin;
import com.brewingjava.mahavir.entities.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.SubCategories;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class CategoriesToDisplayService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public CategoriesToDisplayDao categoriesToDisplayDao;
    
    public ResponseEntity<?> addCategory(String authorization,String category,MultipartFile multipartFile){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            
            admin = adminDao.findByEmail(email);
            
            if(admin!=null){
                CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
                if(existingCategory!=null){
                    responseMessage.setMessage("Category already exist");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                }
                CategoriesToDisplay categoriesToDisplay = new CategoriesToDisplay(category,new Binary(BsonBinarySubType.BINARY,multipartFile.getBytes()));
                categoriesToDisplayDao.save(categoriesToDisplay);
                responseMessage.setMessage("Category added successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }else{
                responseMessage.setMessage("You do not have permission to add categories");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getCategories(){
        try {
            List<CategoriesToDisplay> allCategories = categoriesToDisplayDao.findAll();
            if(allCategories==null){
                responseMessage.setMessage("Sorry....No Categories found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            return ResponseEntity.status(HttpStatus.OK).body(allCategories);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addSubCategory(String authorization,String category,String subCategory){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin!=null){
                CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
                if(existingCategory!=null){
                    List<SubCategories> list= existingCategory.getSubCategories();
                    
                    if(list==null){
                        SubCategories subCategories = new SubCategories();
                        subCategories.setSubCategoryName(subCategory);


                        List<SubCategories> updateSubCategories = new ArrayList<>();
                        updateSubCategories.add(subCategories);

                        existingCategory.setSubCategories(updateSubCategories);
                        categoriesToDisplayDao.save(existingCategory);
                        responseMessage.setMessage("Sub Category Added Successfully");
                        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

                    }
                    ListIterator<SubCategories> subCategoriesList = existingCategory.getSubCategories().listIterator();
                    while(subCategoriesList.hasNext()){
                        if(subCategory.equals(subCategoriesList.next().getSubCategoryName())){
                            responseMessage.setMessage("Sub Category already exists!!");
                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                        }
                    }

                    SubCategories subCategories = new SubCategories();
                    subCategories.setSubCategoryName(subCategory);
                    existingCategory.getSubCategories().add(subCategories);
                    categoriesToDisplayDao.save(existingCategory);
                    responseMessage.setMessage("Category saved successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }else{
                    responseMessage.setMessage("Sorry,No Category found!!");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                }
            }else{
                responseMessage.setMessage("You do not have permission to add sub category");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }

}
