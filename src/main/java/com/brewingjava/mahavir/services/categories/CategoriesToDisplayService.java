package com.brewingjava.mahavir.services.categories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.categories.SubCategories;
import com.brewingjava.mahavir.entities.categories.SubSubCategories;
import com.brewingjava.mahavir.entities.product.ProductInformationItem;
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

import ch.qos.logback.classic.pattern.SyslogStartConverter;

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
                categoriesToDisplay.setSubCategories(new ArrayList<>());
                categoriesToDisplay.setProductInformationItemList(new ArrayList<>());
                
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
            ListIterator<CategoriesToDisplay> listIterator = allCategories.listIterator();
            int i=0;
            while(listIterator.hasNext()){
                if(listIterator.next().getSubCategories()==null){
                    allCategories.get(i).setSubCategories(new ArrayList<>());
                }
                List<SubCategories> subCategories = allCategories.get(i).getSubCategories();
                ListIterator<SubCategories> sCListIterator = subCategories.listIterator();
                int j=0;
                while(sCListIterator.hasNext()){
                    if(sCListIterator.next().getSubSubCategories()==null){
                        allCategories.get(i).getSubCategories().get(j).setSubSubCategories(new ArrayList<>());
                    }
                    int k=0;
                    List<SubSubCategories> subSubCategories = allCategories.get(i).getSubCategories().get(j).getSubSubCategories();
                    ListIterator<SubSubCategories> subSubCatListIterator = subSubCategories.listIterator();
                    while(subSubCatListIterator.hasNext()){
                        if(subSubCatListIterator.next().modelNumber==null){
                            allCategories.get(i).getSubCategories().get(j).getSubSubCategories().get(k).setmodelNumber(new HashSet<>());
                        }
                        k++;
                    }

                    j++;
                }
                i++;
            }
            

            return ResponseEntity.status(HttpStatus.OK).body(allCategories);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getSubCategories(String authorization,String category){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can access it");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);

            }

            CategoriesToDisplay categoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(categoriesToDisplay==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            List<String> subCategoriesName = new ArrayList<>();

            

            List<SubCategories> existingSubCategories = categoriesToDisplay.getSubCategories();
            
            if(existingSubCategories==null){
                return ResponseEntity.status(HttpStatus.OK).body(subCategoriesName);
            }

            ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
            
            

            while(listIterator.hasNext()){
                //System.out.print(listIterator.next());
                subCategoriesName.add(listIterator.next().getSubCategoryName());
            } 
            return ResponseEntity.status(HttpStatus.OK).body(subCategoriesName);
            
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
                        subCategories.setSubSubCategories(new ArrayList<>());


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
                    subCategories.setSubSubCategories(new ArrayList<>());
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
                //Find category that matches with input category
                CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
                if(existingCategoriesToDisplay!=null){
                    List<SubCategories> existingSubCategories = existingCategoriesToDisplay.getSubCategories();
                    //Find subCategory that matches with input subCategory
                    if(existingSubCategories==null){
                        responseMessage.setMessage("Sorry!! No Sub Category found");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                    }
                    ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
                    int i=0;
                    while(listIterator.hasNext()){
                        if(listIterator.next().getSubCategoryName().equalsIgnoreCase(subCategory)){
                            List<SubSubCategories> existingSubSubCategories = existingSubCategories.get(i).getSubSubCategories();
                            //Check if existingSubSubCategories is null
                            if(existingSubSubCategories==null){
                                SubSubCategories subSubCategories = new SubSubCategories();
                                subSubCategories.setSubSubCategoryName(subSubCategory);
                                subSubCategories.setmodelNumber(new HashSet<>());
                                List<SubSubCategories> updatedSubSubCategories = new ArrayList<>();
                                updatedSubSubCategories.add(subSubCategories);        
                                existingSubCategories.get(i).setSubSubCategories(updatedSubSubCategories);
                                existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                                categoriesToDisplayDao.save(existingCategoriesToDisplay);
                                responseMessage.setMessage("Sub Sub Category added Successfully");
                                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                            }
                            //Now we have to check input SubSubCategories is present in list of Existing SubSubCategories or not;
                            ListIterator<SubSubCategories> existingSSCListIterator = existingSubSubCategories.listIterator();
                            while(existingSSCListIterator.hasNext()){
                                if(subSubCategory.equals(existingSSCListIterator.next().subSubCategoryName)){
                                    responseMessage.setMessage("Sub Sub Category Already exists");
                                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                                }
                            }
                            SubSubCategories subSubCategories = new SubSubCategories();
                            subSubCategories.setSubSubCategoryName(subSubCategory);
                            subSubCategories.setmodelNumber(new HashSet<>());
                            existingSubSubCategories.add(subSubCategories);
                            existingSubCategories.get(i).setSubSubCategories(existingSubSubCategories);
                            existingCategoriesToDisplay.setSubCategories(existingSubCategories);
                            categoriesToDisplayDao.save(existingCategoriesToDisplay);
                            responseMessage.setMessage("Sub Sub Category Added Successfully");
                            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                        }
                        i++;
                    }
                    //If not in if cond then out of while loop no sub  category is found
                    responseMessage.setMessage("Sorry!!No Sub Category Found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);

                }else{
                    responseMessage.setMessage("Sorry!! Category Not Found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
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


    public ResponseEntity<?> getSubSubCategories(String authorization,String category,String subCategory){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can access it");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);

            }

            CategoriesToDisplay categoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(categoriesToDisplay==null){
                responseMessage.setMessage("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
            

            List<SubCategories> existingSubCategories = categoriesToDisplay.getSubCategories();
            
            if(existingSubCategories==null){
                responseMessage.setMessage("Sub Category Not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            ListIterator<SubCategories> listIterator = existingSubCategories.listIterator();
            
            
            int j=0;
            while(listIterator.hasNext()){
                //System.out.print(listIterator.next());
                if(subCategory.equalsIgnoreCase(listIterator.next().getSubCategoryName())){
                    //List<SubSubCategories> existingSubSubCategories = listIterator.next().getSubSubCategories();
                    System.out.println(existingSubCategories.get(j).getSubCategoryName());
                    List<SubSubCategories> existingSubSubCategories = existingSubCategories.get(j).getSubSubCategories();
                    List<String> subSubCategories = new ArrayList<>();
                    if(existingSubSubCategories==null){
                        System.out.println("existing Sub Sub Categories is null");
                        return ResponseEntity.status(HttpStatus.OK).body(subSubCategories);
                    }
                    ListIterator<SubSubCategories> ssCListIterator = existingSubSubCategories.listIterator();
                    int k=0;
                    while(ssCListIterator.hasNext()){
                        System.out.println(ssCListIterator.next().getSubSubCategoryName());
                        subSubCategories.add(existingSubSubCategories.get(k).getSubSubCategoryName());
                        k++;
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(subSubCategories);
                }
                j++;

            } 
            responseMessage.setMessage("Sub Category Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }


    public ResponseEntity<?> addProductInformationItems(String authorization,String category,String itemName,List<String> subItemsList){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add product Information list");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            }
            CategoriesToDisplay existingCategoriesToDisplay = categoriesToDisplayDao.findBycategory(category);
            if(existingCategoriesToDisplay==null){
                responseMessage.setMessage("Category Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<ProductInformationItem> existingProductInformationItems = existingCategoriesToDisplay.getProductInformationItemList();
            for(int i=0;i<existingProductInformationItems.size();i++){
                if(existingProductInformationItems.get(i).getitemName().equalsIgnoreCase(itemName)){
                    responseMessage.setMessage("Item already exists");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                }
            }
            
            HashSet<String> hashSet = new HashSet<>();
            for(int i=0;i<subItemsList.size();i++){
                hashSet.add(subItemsList.get(i));
            }
            ProductInformationItem item = new ProductInformationItem(itemName, new ArrayList<>(hashSet));

            existingProductInformationItems.add(item);
            existingCategoriesToDisplay.setProductInformationItemList(existingProductInformationItems);
            categoriesToDisplayDao.save(existingCategoriesToDisplay);
            responseMessage.setMessage("Product Information Item list saved");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
