package com.brewingjava.mahavir.helper;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.model.ParagraphPropertyFetcher.ParaPropFetcher;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

import com.brewingjava.mahavir.daos.HomepageComponents.DealsDao;
import com.brewingjava.mahavir.daos.HomepageComponents.ShopByBrandsDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.categories.ExtraCategories.ParentToDisplayDao;
import com.brewingjava.mahavir.daos.offers.OfferPosterDao;
import com.brewingjava.mahavir.daos.product.FilterCriteriasDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.HomepageComponents.BrandCategory;
import com.brewingjava.mahavir.entities.HomepageComponents.BrandOfferPoster;
import com.brewingjava.mahavir.entities.HomepageComponents.Deals;
import com.brewingjava.mahavir.entities.HomepageComponents.ShopByBrands;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.categories.SubCategories;
import com.brewingjava.mahavir.entities.categories.SubSubCategories;
import com.brewingjava.mahavir.entities.categories.ExtraCategories.Parent;
import com.brewingjava.mahavir.entities.offers.OfferPosters;
import com.brewingjava.mahavir.entities.product.Factors;
import com.brewingjava.mahavir.entities.product.FilterCriterias;
import com.brewingjava.mahavir.entities.product.FreeItem;
import com.brewingjava.mahavir.entities.product.ProductDescription;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.product.ProductVariants;

import lombok.val;
import lombok.var;

@Component
public class ExcelHelper {

    
   


    //check file is excel or not
    public static boolean checkFileType(MultipartFile multipartFile){
        String contentType = multipartFile.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            return true;
        return false;
    }

    public ResponseEntity<?> addProducts(InputStream inputStream) {
        try {
            String message="";
            DataFormatter formatter = new DataFormatter();
            PictureData pict;
            byte[] data;
            String value;
            URL imageUrl;
            String fileName;
            MultipartFile multipartFile;
            BufferedImage image;
            ByteArrayOutputStream byteArrayOutputStream;

            XSSFWorkbook workbook =  new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Products");
            int rowNumber=0;

            Iterator<Row> iterator = sheet.iterator();
            FreeItem freeItem=null;
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                ProductDetail productDetail = new ProductDetail();
                
                String destinationFile = "sample.jpg";
                boolean flag = true;
                
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")){
                                    productDetail=null;
                                    break;
                                }
                                productDetail.setProductId(value);
                                
                            } catch (Exception e) {
                                System.out.println("Product Id:"+formatter.formatCellValue(cell));
                                // e.printStackTrace();
                                flag = false;
                                //TODO: handle exception
                            }
                        case 1:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")){
                                    productDetail=null;
                                    break;
                                }
                                productDetail.setModelNumber(value);
                            } catch (Exception e) {
                                System.out.println("Model Number:"+formatter.formatCellValue(cell));
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 2:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")){
                                    productDetail.setProductName("");
                                    break;
                                } 
                                productDetail.setProductName(value);
                            } catch (Exception e) {
                                System.out.println("Product Name:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 3:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductImage1(value);
                                // imageUrl = new URL(value);
                                // image = ImageIO.read(imageUrl);
                                // byteArrayOutputStream = new ByteArrayOutputStream();
                                // ImageIO.write(image,"jpeg",byteArrayOutputStream);
                                // fileName = "sample.jpeg";
                                // multipartFile = new MockMultipartFile(fileName,fileName,"jpeg",byteArrayOutputStream.toByteArray());
                                // productDetail.setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                System.out.println("Product Image 1:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 4:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductImage2(value);
                                // imageUrl = new URL(value);
                                // image = ImageIO.read(imageUrl);
                                // byteArrayOutputStream = new ByteArrayOutputStream();
                                // ImageIO.write(image,"jpeg",byteArrayOutputStream);
                                // fileName = "sample.jpeg";
                                // multipartFile = new MockMultipartFile(fileName,fileName,"jpeg",byteArrayOutputStream.toByteArray());
                                // productDetail.setProductImage2(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                System.out.println("Product Image 2:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 5:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductImage3(value);
                                // imageUrl = new URL(value);
                                // image = ImageIO.read(imageUrl);
                                // byteArrayOutputStream = new ByteArrayOutputStream();
                                // ImageIO.write(image,"jpeg",byteArrayOutputStream);
                                // fileName = "sample.jpeg";
                                // multipartFile = new MockMultipartFile(fileName,fileName,"jpeg",byteArrayOutputStream.toByteArray());
                                // productDetail.setProductImage3(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                System.out.println("Product Image 3:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 6:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductImage4(value);
                                // imageUrl = new URL(value);
                                // image = ImageIO.read(imageUrl);
                                // byteArrayOutputStream = new ByteArrayOutputStream();
                                // ImageIO.write(image,"jpeg",byteArrayOutputStream);
                                // fileName = "sample.jpeg";
                                // multipartFile = new MockMultipartFile(fileName,fileName,"jpeg",byteArrayOutputStream.toByteArray());
                                // productDetail.setProductImage4(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                System.out.println("Product Image 4:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 7:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductImage5(value);
                                // imageUrl = new URL(value);
                                // image = ImageIO.read(imageUrl);
                                // byteArrayOutputStream = new ByteArrayOutputStream();
                                // ImageIO.write(image,"jpeg",byteArrayOutputStream);
                                // fileName = "sample.jpeg";
                                // multipartFile = new MockMultipartFile(fileName,fileName,"jpeg",byteArrayOutputStream.toByteArray());
                                // productDetail.setProductImage5(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                System.out.println("Product Image 5:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 8:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setProductPrice(value);
                            } catch (Exception e) {
                                System.out.println("Product Price:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 9:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setOfferPrice(value);
                            } catch (Exception e) {
                                System.out.println("Offer Price:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 10:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                productDetail.setCategory(value);
                            } catch (Exception e) {
                                System.out.println("Categorys:"+productDetail.getModelNumber());
                                // e.printStackTrace();
                                flag = false;
                            }
                        break;
                        case 11:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) {
                                    productDetail.setProductHighlights("");
                                    break;
                                }productDetail.setProductHighlights(value);
                            } catch (Exception e) {
                                System.out.println("Product Highlights:"+productDetail.getModelNumber());
                                flag=false;
                                // e.printStackTrace();
                                // responseMessage.setMessage(e.getMessage());
                                // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
                            }
                        break;
                        case 12:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("")){
                                    productDetail.setSubCategoryMap(new HashMap<>());
                                    break;
                                } 
                                HashMap<String,String> map = new HashMap<>();
                                String arr[]= value.split(",");
                                for(int i=0;i<arr.length;i++){
                                    String subCat[] = arr[i].split(":");
                                    map.put(subCat[0],subCat[1]);
                                }
                                productDetail.setSubCategoryMap(map);
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Sub Categories:"+productDetail.getModelNumber());
                                // System.out.println(formatter.formatCellValue(cell));
                                // e.printStackTrace();
                            }
                            
                        break;
                        case 13:
                            try {
                                HashMap<String,HashMap<String,String>> productInfo = new HashMap<>();
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")){
                                    productDetail.setProductInformation(new HashMap<>());
                                    break;
                                }
                                String array[] = value.split("#");
                                for(int i=0;i<array.length;i++){
                                    //System.out.println(array[i]);
                                    String subSplit[] = array[i].split("\\[");
                                    HashMap<String,String> mp = new HashMap<>();
                                // System.out.println(subSplit.length);
                                    String x = subSplit[1];
                                    String innermap = x.substring(0,x.length()-1);
                                    //System.out.println("0:"+subSplit[0]+"\t1:"+innermap);
                                    String keyValue[] = innermap.split(";");
                                    for(int j=0;j<keyValue.length;j++){
                                        //System.out.println("KeyValue:"+keyValue[j]);
                                        String pair[] = keyValue[j].split("=");
                                        try {
                                            //System.out.println("pair[0]="+pair[0]+"\tpair[1]="+pair[1]);
                                            mp.put(pair[0],pair[1]);
                                        } catch (Exception e) {
                                            // e.printStackTrace();
                                            flag = false;
                                            System.out.println("Product Information:"+productDetail.getModelNumber());
                                        }
                                    }
                                    productInfo.put(subSplit[0],mp);
                                }
                                productDetail.setProductInformation(productInfo);
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Product Information:"+productDetail.getModelNumber());
                                // System.out.println(formatter.formatCellValue(cell));
                                // e.printStackTrace();
                            }
                        break;
                        case 14:
                            try {
                                HashMap<String,String> productVariants = new HashMap<>();
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null) break;
                                if(value.trim().equals("-")){
                                    productDetail.setVariants(new HashMap<>());
                                    break;
                                }
                                String array[] = value.split(";");
                                for(int i=0;i<array.length;i++){
                                    // array[i] = array[i].trim();
                                    String pair[] = array[i].split("=");
                                    // System.out.println("pair[0]="+pair[0]+"\tpair[1]="+pair[1]);
                                    productVariants.put(pair[0],pair[1]);
                                }
                                System.out.println(productDetail.getModelNumber()+"\t"+productVariants);
                                // productDetail.setVariants(productVariants);
                                productDetail.setVariants(productVariants);
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Product Variants:"+productDetail.getModelNumber());
                                // System.out.println(formatter.formatCellValue(cell));
                                // e.printStackTrace();
                            }
                        break;
                        case 15:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null || value.trim().equals("-")) break;
                                String array[] = value.split(";");
                                ArrayList<String> defaultVariants = new ArrayList<>();
                                for(int i=0;i<array.length;i++){
                                    defaultVariants.add(array[i].trim());
                                }
                                productDetail.setDefaultVariant(defaultVariants);
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Default variants:"+productDetail.getModelNumber());
                            }
                        break;
                        case 16:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null) break;
                                if(value.trim().equals("-")){
                                    productDetail.setVariantTypes(new HashMap<>());
                                    break;
                                }
                                HashMap<String,ArrayList<String>> variantTypes = new HashMap<>();
                                String array[] = value.split("#");
                                for(int i=0;i<array.length;i++){
                                    String pair[] = array[i].split("\\[");
                                    pair[1] = pair[1].substring(0,pair[1].length()-1);
                                    String values[] = pair[1].split(";");
                                    ArrayList<String> list = new ArrayList<>();
                                    for(int j=0;j<values.length;j++){
                                        list.add(values[j]);
                                    }
                                    System.out.println("pair[0]="+pair[0]+"\tpair[1]="+pair[1]);
                                    variantTypes.put(pair[0],list);
                                }
                                productDetail.setVariantTypes(variantTypes);

                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Product Variant Types:"+productDetail.getModelNumber());
                            }
                        break;
                        case 17:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")){    
                                    // System.out.println("In if free item"); 
                                    freeItem = null; 
                                    break;
                                }
                                freeItem = new FreeItem();
                                String arrays[] = value.split(";");
                                freeItem.setName(arrays[0]);
                                freeItem.setPrice(arrays[1]);
                                imageUrl = new URL(arrays[2]);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
    
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                freeItem.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                                System.out.println(freeItem.toString());                    
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Free Item:"+productDetail.getModelNumber());
                                // System.out.println(formatter.formatCellValue(cell));
                                // e.printStackTrace();
                            }
                        break;
                        case 18:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null) break;
                                if(value.trim().equals("")){
                                    productDetail.setFiltercriterias(new HashMap<>());
                                    break;
                                }
                                HashMap<String,String> filterCriterias = new HashMap<>();
                                String keyValue[] = value.split(";");
                                for(int i=0;i<keyValue.length;i++){
                                    String pair[] = keyValue[i].split("=");
                                    filterCriterias.put(pair[0].trim(),pair[1].trim());
                                }
                                productDetail.setFiltercriterias(filterCriterias);
                                
                            } catch (Exception e) {
                                flag = false;
                                System.out.println("Filter Criterias:"+productDetail.getModelNumber());
                                // System.out.println(formatter.formatCellValue(cell));
                                // e.printStackTrace();
                            }   
                        break;
                        case 19:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(productDetail==null) break;
                                if(value.trim().equals("-")){
                                    break;
                                }
                                String[] productDesc = value.split("#");
                                ArrayList<ProductDescription> list  = new ArrayList<>(); 
                                for(int i=0;i<productDesc.length;i++){
                                    String product[] = productDesc[i].split(";");
                                    ProductDescription productDescription = new ProductDescription(product[0],product[1],product[2]);
                                    list.add(productDescription);
                                }
                                productDetail.setProductDescriptions(list);

                            } catch (Exception e) {
                                // e.printStackTrace();
                                System.out.println("Product Description:"+productDetail.getModelNumber());
                                // responseMessage.setMessage(e.getMessage());
                                // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
                            }

                        default:
                        break;
                    }
                    cid++;
                }
                try {
                    if(flag || productDetail!=null || !productDetail.getModelNumber().equals(""))
                        productDetailsDao.save(productDetail);
                    else{
                        System.out.println("Product Details not saved"+productDetail.getModelNumber());
                        message+=productDetail.getModelNumber()+",";
                    }
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                rowNumber++;
            }
            responseMessage.setMessage("Products saved successfully\nNot Saved for:"+message);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public ResponseMessage responseMessage;

   
    @Autowired
    public CategoriesToDisplayDao categoriesToDisplayDao;


    public String addCategories(InputStream is){
        try {
            categoriesToDisplayDao.deleteAll();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Categories");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            URL imageUrl;
            String fileName;
            MultipartFile multipartFile;
            BufferedImage image;
            ByteArrayOutputStream byteArrayOutputStream;
            List<SubSubCategories> subSubCategoriesList = new ArrayList<>();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                CategoriesToDisplay categoriesToDisplay = null;
                List<SubCategories> subCategoriesList = new ArrayList<>();
                SubCategories subCat = null;
                SubSubCategories subSubCategories = null;
                // subSubCategoriesList = new ArrayList<>();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("-")) break;
                            categoriesToDisplay = new CategoriesToDisplay();
                            categoriesToDisplay.setCategory(value);
                            CategoriesToDisplay existingCat = categoriesToDisplayDao.findBycategory(value);
                            if(existingCat!=null){
                                categoriesToDisplay = existingCat;
                                subCategoriesList = existingCat.getSubCategories();
                            }
                            // }else{
                            //     subCategoriesList = new ArrayList<>();
                            // }
                            
                        break;
                        case 1:
                            if(categoriesToDisplay==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            categoriesToDisplay.setCategory_image(value);
                        break;
                        case 2:
                            if(categoriesToDisplay==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value); //subCat  name
                            if(value.trim().equals("-")) break;
                            //already subCategory exists will be removed
                            for(int i=0;i<subCategoriesList.size();i++){
                                if(subCategoriesList.get(i).getSubCategoryName().equals(value)){
                                    subSubCategoriesList = subCategoriesList.get(i).getSubSubCategories();
                                    subCategoriesList.remove(i);
                                }
                            }
                            subCat = new SubCategories();
                            subCat.setSubCategoryName(value);      //Brand set as subCategory                       
                        break;
                        case 3:
                            if(categoriesToDisplay==null || subCat==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value); //subSubCat  name
                            if(value.trim().equals("")) break;
                            subSubCategories = new SubSubCategories();
                            subSubCategories.setSubSubCategoryName(value); //MI
                            subSubCategories.setmodelNumber(new HashSet<>());
                        break;
                        case 4:
                            if(categoriesToDisplay==null || subSubCategories==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value); //Model nos
                            if(value.trim().equals("")) break;
                            String[] modelNos = value.split(";");
                            HashSet<String> modelNoSet = new HashSet<>();
                            for(int i=0;i<modelNos.length;i++){
                                modelNoSet.add(modelNos[i]);
                            }
                            subSubCategories.setmodelNumber(modelNoSet);
                            subSubCategoriesList.add(subSubCategories);
                            subCat.setSubSubCategories(subSubCategoriesList);   
                            subCategoriesList.add(subCat);
                            categoriesToDisplay.setSubCategories(subCategoriesList);
                            categoriesToDisplayDao.save(categoriesToDisplay);
                            subSubCategoriesList.clear();
                            subCategoriesList.clear();
                            
                        break;

                        default:
                        break;
                    }
                    cid++;
                    
                }
                // if(categoriesToDisplay!=null){

                //     // subCat.setSubSubCategories(subSubCategoriesList);
                //     // subCategorisList.add(subCat);
                    
                // }
                rowNumber++;
            }

            return "Categories added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    @Autowired
    public FilterCriteriasDao filterCriteriasDao;

    public ResponseEntity<?> addFilterCriterias(InputStream is){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Filter Criterias");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            URL imageUrl;
            String fileName;
            MultipartFile multipartFile;
            BufferedImage image;
            ByteArrayOutputStream byteArrayOutputStream;
            List<SubSubCategories> subSubCategoriesList = new ArrayList<>();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                FilterCriterias filterCriterias = new FilterCriterias();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("")){
                                    filterCriterias = null;
                                    break;
                                };
                                filterCriterias = new FilterCriterias();
                                filterCriterias.setCategory(value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                        break;
                        case 1:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("") || filterCriterias==null) break;
                                HashMap<String,ArrayList<String>> map = new HashMap<>();
                                String values[] = value.split("#");
                                for(int i=0;i<values.length;i++){
                                    System.out.println(values[i]);
                                    String[] keyValue = values[i].split("\\["); //map key in keyvalue[0]
                                    String list_val = keyValue[1].substring(0,keyValue[1].length()-1); 
                                    String[] list = list_val.split(";");
                                    ArrayList<String> list_values = new ArrayList<>();
                                    for(int j=0;j<list.length;j++){
                                        list_values.add(list[j].trim());
                                    }
                                    map.put(keyValue[0],list_values);
                            }
                            filterCriterias.setFilterCriterias(map);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                        break;
                        default:
                        break;
                    }
                    cid++;
                }
                rowNumber++;
                if(filterCriterias!=null)
                    filterCriteriasDao.save(filterCriterias);
            }
            responseMessage.setMessage("Product saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
 

   


    @Autowired
    public OfferPosterDao offerPosterDao;

    public ResponseEntity<?> addOfferPosters(InputStream is){
        try {
            offerPosterDao.deleteAll();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("MegaMiniPosters");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            URL imageUrl;
            String fileName;
            MultipartFile multipartFile;
            BufferedImage image;
            ByteArrayOutputStream byteArrayOutputStream;
            
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                OfferPosters offerPosters = new OfferPosters();
                while(cells.hasNext()){
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cells.next());
                            if(value.trim().equals("")){
                                offerPosters = null;
                                break;
                            };
                            offerPosters.setCategory(value);
                        break;
                        case 1:
                            value = formatter.formatCellValue(cells.next());
                            if(offerPosters==null) break;
                            System.out.println(value);
                            // imageUrl = new URL(value);
                            // image = ImageIO.read(imageUrl);
                            // byteArrayOutputStream = new ByteArrayOutputStream();
                            // ImageIO.write(image,"jpg",byteArrayOutputStream);
                            // fileName = "sample.jpg";
                            // multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            
                            offerPosters.setImageUrl(value);
                        break;
                        case 2:
                            value = formatter.formatCellValue(cells.next());
                            if(offerPosters==null) break;
                            if(value.trim().equals("")) break;
                            String modelNumbers[] = value.split(";");
                            offerPosters.setModelNumbers(Arrays.asList(modelNumbers));
                        break;
                        case 3:
                            value = formatter.formatCellValue(cells.next());
                            if(offerPosters==null) break;
                            if(value.trim().equals("")) break;
                            offerPosters.setIsMegaPoster(value.toUpperCase());
                            offerPosterDao.save(offerPosters);
                        break;
                        default:
                        break;
                    }
                    cid++;
                }
                
                rowNumber++;
            }
            responseMessage.setMessage("OfferPosters added Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    public ShopByBrandsDao shopByBrandsDao;

    public ResponseEntity<?> addBrands(InputStream is){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("ShopByBrands");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                ShopByBrands shopByBrands = new ShopByBrands();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("")){
                                    shopByBrands = null;
                                    break;
                                };
                                shopByBrands = new ShopByBrands();
                                shopByBrands.setBrandName(value);
                                System.out.println("value:"+value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                        break;
                        case 1:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(shopByBrands==null || value.trim().equals("-")) break;
                                shopByBrands.setBrandLogo(value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;
                        case 2:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(shopByBrands==null || value.trim().equals("-")) break;
                                ArrayList<BrandOfferPoster> list = new ArrayList<>();
                                String arr[] = value.split("#");
                                for(int i=0;i<arr.length;i++){
                                    String offerPoster[] = arr[i].split("\\[");
                                    BrandOfferPoster brandOfferPoster = new BrandOfferPoster();
                                    brandOfferPoster.setOfferPoster(offerPoster[0]);
                                    
                                    HashSet<String> categories = new HashSet<>();
                                    HashSet<String> modelNumbers = new HashSet<>(); 
                                    offerPoster[1] = offerPoster[1].substring(0,offerPoster[1].length()-1);
                                    String models[] = offerPoster[1].split(";");
                                    for(int j=0;j<models.length;j++){
                                        ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(models[j]);
                                        if(productDetail==null) continue;
                                        categories.add(productDetail.getCategory());
                                        modelNumbers.add(models[j]);
                                    }
                                    ArrayList<String> cat = new ArrayList<>(categories);
                                    ArrayList<String> mod = new ArrayList<>(modelNumbers);
                                    brandOfferPoster.setCategories(cat);
                                    brandOfferPoster.setModelNumbers(mod);
                                    list.add(brandOfferPoster);
                                }
                                shopByBrands.setBrandOfferPosters(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;
                        case 3:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(shopByBrands==null || value.trim().equals("-")) break;
                                ArrayList<BrandCategory> list = new ArrayList<>();
                                String arr[] = value.split("#");
                                
                                for(int i=0;i<arr.length;i++){
                                    String category[] = arr[i].split("\\[");
                                    BrandCategory brandCategory = new BrandCategory();
                                    brandCategory.setCategory(category[0]);
                                    HashSet<String> modelNumbers = new HashSet<>(); 
                                    category[1] = category[1].substring(0,category[1].length()-1);
                                    String models[] = category[1].split(";");
                                    for(int j=0;j<models.length-1;j++){
                                        modelNumbers.add(models[j]);
                                    }
                                    ArrayList<ProductDetail> products = new ArrayList<>();
                                    for(String model:modelNumbers){
                                        ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(model);
                                        if(productDetail==null) continue;
                                        products.add(productDetail);
                                    }

                                    ArrayList<String> mod = new ArrayList<>(modelNumbers);
                                    
                                    brandCategory.setProducts(products);
                                    brandCategory.setCatImage(models[models.length-1]);
                                    list.add(brandCategory);
                                }
                                shopByBrands.setBrandCategories(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;
                        case 4:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(shopByBrands==null || value.trim().equals("-")) break;
                                String videoLinks[] = value.split("#");
                                ArrayList<String> list = new ArrayList<>();
                                for(int i=0;i<videoLinks.length;i++){
                                    list.add(videoLinks[i]);
                                }
                                shopByBrands.setVideoLinks(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;
                        default:
                        break;
                    }
                    cid++;
                }
                if(shopByBrands!=null)
                    shopByBrandsDao.save(shopByBrands);
                rowNumber++;
            }
            responseMessage.setMessage("Brands added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    public DealsDao dealsDao;

    public ResponseEntity<?> addDeals(InputStream is){
        try {
            System.out.println("In deals");
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Deals");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                dealsDao.deleteAll();
                Row row = iterator.next();
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                String t;
                int cid=0;
                Deals deals = new Deals();
                HashSet<ProductsDetailsResponse> products = new HashSet<>();
                HashSet<String> categories = new HashSet<>();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("-"))
                            {
                                deals = null;
                                break;
                            }
                            System.out.println(value);
                            t = value;
                            deals.setTitle(value);
                        break;
                        case 1:
                            value = formatter.formatCellValue(cell);
                            System.out.println("In case 1");
                            if(deals==null || value.trim().equals("")) break;
                            System.out.println(value);
                            String mNums[] = value.split(";");
                            for(int i=0;i<mNums.length;i++){
                                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(mNums[i]);
                                
                                if(productDetail!=null){
                                    ProductsDetailsResponse productsDetailsResponse = new ProductsDetailsResponse();
                                    productsDetailsResponse.setModelNumber(productDetail.getModelNumber());
                                    productsDetailsResponse.setProductName(productDetail.getProductName());
                                    productsDetailsResponse.setProductPrice(productDetail.getProductPrice());
                                    productsDetailsResponse.setCategory(productDetail.getCategory());
                                    productsDetailsResponse.setSubCategoryMap(productDetail.getSubCategoryMap());
                                    productsDetailsResponse.setOfferPrice(productDetail.getOfferPrice());
                                    productsDetailsResponse.setProductHighlights(productDetail.getProductHighlights());
                                    productsDetailsResponse.setProductImage1(productDetail.getProductImage1());
                                    products.add(productsDetailsResponse);
                                    categories.add(productDetail.getCategory());
                                }
                            }
                            // System.out.println("Products:"+products);
                            // System.out.println("\nCategories:"+categories);
                            // Deals d = new Deals(t,products,new ArrayList<>(categories));
                            // deals.setProducts(products);
                            // deals.setCategories(new ArrayList<>(categories));
                            System.out.println("Deals:\n"+deals);
                            dealsDao.save(deals);
                            System.out.println("Saved");
                        break;
                        default:
                        break;
                    }
                    cid++;   
                }
                rowNumber++;
            }
            responseMessage.setMessage("Deals added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addCategoryIsInNavBar(InputStream is){
        try {
            System.out.println("In addCategoryIsInNavBar");
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Is In Navbar");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                // dealsDao.deleteAll();
                Row row = iterator.next();
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                String t;
                int cid=0;
                CategoriesToDisplay categoriesToDisplay = null;
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            t = value;
                            categoriesToDisplay = categoriesToDisplayDao.findBycategory(value);
                            break;
                        case 1:
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.equalsIgnoreCase("Yes") && categoriesToDisplay!=null){
                                categoriesToDisplay.setInNavbar(true);
                                categoriesToDisplayDao.save(categoriesToDisplay);
                            }else if(value.equalsIgnoreCase("No") && categoriesToDisplay!=null){
                                categoriesToDisplay.setInNavbar(false);
                                categoriesToDisplayDao.save(categoriesToDisplay);
                            }
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
            }
            responseMessage.setMessage("Categories Is In Navbar saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @Autowired
    private ParentToDisplayDao parentToDisplayDao;

    public ResponseEntity<?> addParentCategories(InputStream inputStream) {
        try {
            System.out.println("In addParentCategories");
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Extra Categories");
            int rowNumber=0;
            DataFormatter formatter = new DataFormatter();
            String value;
            Iterator<Row> iterator = sheet.iterator();
            
            while(iterator.hasNext()){
                // dealsDao.deleteAll();
                Row row = iterator.next();
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                String t;
                int cid=0;
                Parent parent = new Parent();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            parent.setParentName(value);
                            break;
                        case 1:
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            String arr[] = value.split(";");
                            ArrayList<CategoriesToDisplay> list = new ArrayList<>();

                            for(int i=0;i<arr.length;i++){
                                CategoriesToDisplay categoriesToDisplay = categoriesToDisplayDao.findBycategory(arr[i]);
                                if(categoriesToDisplay!=null){
                                    list.add(categoriesToDisplay);
                                }
                            }
                            parent.setCategories(list);
                            parentToDisplayDao.save(parent);
                            break;
                        default:
                            break;
                    }
                    cid++;     
                }
            }
            responseMessage.setMessage("Parent Categories saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }



}