package com.brewingjava.mahavir.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import com.brewingjava.mahavir.daos.AdminDao;
import com.brewingjava.mahavir.daos.CategoriesToDisplayDao;
import com.brewingjava.mahavir.entities.Admin;
import com.brewingjava.mahavir.entities.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.SubCategories;
import com.brewingjava.mahavir.entities.SubSubCategories;
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

    public ResponseEntity<?> addSubSubCategory(String authorization,String category,String subCategory,String subSubCategory){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin!=null){
                //Find category from category name
                //Find subcategories of that category
                //Add sub sub category to required subCategory
                //Save this category in database
                CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
                if(existingCategory!=null){
                    List<SubCategories> existingSubCategories = existingCategory.getSubCategories();
                    if(existingSubCategories!=null){
                        ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
                        System.out.println("existing subcategory not null");
                        int i=0;
                        while(listIterator.hasNext()){
                            if(subCategory.equals(listIterator.next().getSubCategoryName())){
                                System.out.println("subcategory found");
                                HashSet<SubSubCategories> existingSubSubCategories = existingSubCategories.get(i).getSubSubCategories();
                                System.out.println("existing sub sub category not null");
                                if(existingSubSubCategories == null){
                                    System.out.println("subsubcategory is null");
                                    SubSubCategories subSubCategories = new SubSubCategories();
                                    subSubCategories.setSubSubCategoryName(subSubCategory);
                                    HashSet<SubSubCategories> updateSubSubCategories = new HashSet<>();
                                    updateSubSubCategories.add(subSubCategories);
                                    // listIterator.next().setSubSubCategories(updateSubSubCategories);
                                    existingSubCategories.get(i).setSubSubCategories(updateSubSubCategories);
                                    existingCategory.setSubCategories(existingSubCategories);
                                    categoriesToDisplayDao.save(existingCategory);
                                    responseMessage.setMessage("Sub Sub Category Added Successfully");
                                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                                }
                                System.out.println("malsjdbju");
                                // for(int j = 0; j < existingSubSubCategories.size(); j++){
                                //     if(subSubCategory.equals(existingSubSubCategories.get(j).getSubSubCategoryName())){
                                //         responseMessage.setMessage("Sub Sub Category already exists!!");
                                //         return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                                //     }
                                //     else{
                                //         List<SubSubCategories> newSubSubCategories = new ArrayList<>();
                                //         SubSubCategories updatedSubSubCategories = new SubSubCategories();
                                //         updatedSubSubCategories.setSubSubCategoryName(subSubCategory);
                                //         newSubSubCategories.add(updatedSubSubCategories);
                                //         existingSubCategories.get(i).setSubSubCategories(newSubSubCategories);
                                //         // listIterator.next().setSubSubCategories(newSubSubCategories);
                                //         existingCategory.setSubCategories(existingSubCategories);
                                //         categoriesToDisplayDao.save(existingCategory);
                                //         responseMessage.setMessage("Sub Sub Category added successfully");
                                //         return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                                //     }
                                // }
                                // if(existingSubSubCategories==null){
                                if(existingSubCategories.contains(subSubCategory)){
                                    responseMessage.setMessage("Sub Sub Category already exists!!");
                                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                                }
                                else{
                                    SubSubCategories subSubCategories = new SubSubCategories();
                                    subSubCategories.setSubSubCategoryName(subSubCategory);
                                    // existingSubCategories.get(i).getSubSubCategories().add(subSubCategories);
                                    // categoriesToDisplayDao.save(existingCategory);
                                    existingSubSubCategories.add(subSubCategories);
                                    existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                    existingCategory.setSubCategories(existingSubCategories);
                                    categoriesToDisplayDao.save(existingCategory);
                                    responseMessage.setMessage("Sub Sub Category added successfully");
                                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                                }
                                // ListIterator<SubSubCategories> existingSubSubCategoriesListIterator = existingSubSubCategories.listIterator();
                                // while(existingSubSubCategoriesListIterator.hasNext()){
                                //     if(subSubCategory.equals(existingSubSubCategoriesListIterator.next())){
                                //         responseMessage.setMessage("Sub Sub Category already exist");
                                //         return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                                //     }
                                // }
                                // SubSubCategories subSubCategories = new SubSubCategories();
                                // subSubCategories.setSubSubCategoryName(subSubCategory);
                                // existingSubSubCategories.add(subSubCategories);
                                // existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                                // // listIterator.next().setSubSubCategories(existingSubSubCategories);
                                // existingCategory.setSubCategories(existingSubCategories);
                                // categoriesToDisplayDao.save(existingCategory);
                                // responseMessage.setMessage("Sub Sub Category added successfully");
                                // return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                            }   
                            i++;
                        }
                        responseMessage.setMessage("Sorry!! No Sub Category found..");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);

                    }else{
                        responseMessage.setMessage("Sorry!! No Sub Category found..");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                    }
                }else{
                    responseMessage.setMessage("Sorry!! Category Not found..");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                }

            }else{
                responseMessage.setMessage("Yo do Not have permission to Add Sub Sub Category Successfully");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    // public ResponseEntity<?> addSubSubCategory(String authorization,String category,String subCategory,String subSubCategory){
    //     try {
    //         String token = authorization.substring(7);
    //         String email = jwtUtil.extractUsername(token);
    //         admin = adminDao.findByEmail(email);
    //         if(admin!=null){
    //             CategoriesToDisplay existingCategory = categoriesToDisplayDao.findBycategory(category);
    //             if(existingCategory!=null){
    //                 List<SubCategories> list= existingCategory.getSubCategories();
                    
    //                 if(list==null){
    //                     responseMessage.setMessage("No Sub Category found");
    //                     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    //                 }

    //                 ListIterator<SubCategories> subCategoriesList = existingCategory.getSubCategories().listIterator();
    //                 while(subCategoriesList.hasNext()){
    //                     if(subCategory.equals(subCategoriesList.next().getSubCategoryName())){
                            
    //                         List<String> subSubCategoriesList = subCategoriesList.next().getSubSubCategories();
    //                         if(subSubCategoriesList==null){
    //                             List<String> updatedSubSubCategoriesList = new ArrayList<>();
    //                             updatedSubSubCategoriesList.add(subSubCategory);
    //                             SubCategories object = new SubCategories(subCategory,subSubCategoriesList);
    //                             List<SubCategories> new_sSubCategories = new ArrayList<>();
    //                             new_sSubCategories.add(object);
    //                             existingCategory.setSubCategories(new_sSubCategories);
    //                             categoriesToDisplayDao.save(existingCategory);
    //                             responseMessage.setMessage("Sub Sub Category Added Successfully");
    //                             return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    //                         }
    //                         subSubCategoriesList.add(subSubCategory);
    //                         SubCategories object = new SubCategories(subCategory,subSubCategoriesList);
    //                         List<SubCategories> new_sSubCategories = new ArrayList<>();
    //                         new_sSubCategories.add(object);
    //                         existingCategory.setSubCategories(new_sSubCategories);
    //                         categoriesToDisplayDao.save(existingCategory);
    //                         responseMessage.setMessage("Sub Sub Category Added Successfully");
    //                         return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    //                     }
    //                 }
    //                 responseMessage.setMessage("No Sub Category found");
    //                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                    


    //             }else{
    //                 responseMessage.setMessage("Sorry, No category found!!");
    //                 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
    //             }
    //         }else{
    //             responseMessage.setMessage("You do not have permisssions to add Sub Sub Category");
    //             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
    //         }

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         responseMessage.setMessage(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    //     }

    // }
}
