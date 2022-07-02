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

import com.brewingjava.mahavir.daos.HomepageComponents.ShopByBrandsDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.offers.OfferPosterDao;
import com.brewingjava.mahavir.daos.product.FilterCriteriasDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.HomepageComponents.BrandCategory;
import com.brewingjava.mahavir.entities.HomepageComponents.BrandOfferPoster;
import com.brewingjava.mahavir.entities.HomepageComponents.ShopByBrands;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.categories.SubCategories;
import com.brewingjava.mahavir.entities.categories.SubSubCategories;
import com.brewingjava.mahavir.entities.offers.OfferPosters;
import com.brewingjava.mahavir.entities.product.Factors;
import com.brewingjava.mahavir.entities.product.FilterCriterias;
import com.brewingjava.mahavir.entities.product.FreeItem;
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

    public List<ProductDetail> convertExcelToListOfProductDetails(InputStream is){
        List<ProductDetail> list = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();
        PictureData pict;
        byte[] data;
        String value;
        URL imageUrl;
        String fileName;
        MultipartFile multipartFile;
        BufferedImage image;
        ByteArrayOutputStream byteArrayOutputStream;
        
        try {
            XSSFWorkbook workbook =  new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Whirlpool");
            int rowNumber=0;

            Iterator<Row> iterator = sheet.iterator();
            FreeItem freeItem=null;
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<=1){
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
                            // int s = (int)cell.getNumericCellValue();
                            try {
                                value = formatter.formatCellValue(cell);
                                productDetail.setModelNumber(value);    
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }    
                        break;
                        case 1:
                            try {
                                value = formatter.formatCellValue(cell);
                                productDetail.setProductName(value);
                                        
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                        break;                        
                        
                        case 2:
                            try {  
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                imageUrl = new URL(value);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                productDetail.setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {  
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                imageUrl = new URL(value);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                productDetail.setProductImage2(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                        break;
                        case 4:
                            try{
                                value = formatter.formatCellValue(cell);
                            if(value.trim().equals("-")) break;
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage3(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));

                            }catch(Exception e){
                                flag = false;
                                e.printStackTrace();
                            }
                            
                            break;
                        case 5:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                imageUrl = new URL(value);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                productDetail.setProductImage4(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            
                            break;
                        case 6:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                imageUrl = new URL(value);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                productDetail.setProductImage5(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                        break;
                        
                        case 7:
                            try{
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                productDetail.setProductPrice(value);
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            
                            break;
                        case 8:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                productDetail.setOfferPrice(value);    
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
                                value = formatter.formatCellValue(cell);
                                productDetail.setCategory(value);
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            break;
                        case 10:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")) break;
                                productDetail.setProductHighlights(value);
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            
                            break;
                        case 11:
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
                                e.printStackTrace();
                            }
                            
                        break;
                        case 12:
                            try {
                                HashMap<String,HashMap<String,String>> productInfo = new HashMap<>();
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("")){
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
                                            e.printStackTrace();
                                        }
                                    }
                                    productInfo.put(subSplit[0],mp);
                                }
                                productDetail.setProductInformation(productInfo);
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            break;
                        case 13:
                            try {
                                HashMap<String,ArrayList<String>> productVariants = new HashMap<>();
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("")){
                                    System.out.println("Empty");
                                    productDetail.setVariants(new HashMap<>());
                                    break;
                                }
                                System.out.println(value);
                                String variants[] = value.split("#");
                                for(int i=0;i<variants.length;i++){
                                    System.out.println(variants[i]);
                                    String factors[] = variants[i].split("\\[");
                                    String x = factors[1].substring(0,factors[1].length()-1);  
                                    String values[] = x.split(";");
                                    System.out.println("key:"+factors[0]+"\tvalues:"+x);
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    for(int j=0;j<values.length;j++) arrayList.add(values[j]);
                                    productVariants.put(factors[0], arrayList);
                                }
                                productDetail.setVariants(productVariants);
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }
                            break;

                        case 14:
                            try {
                                value = formatter.formatCellValue(cell);
                                if(value.trim().equals("-")){    
                                    System.out.println("In if free item"); 
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
                                e.printStackTrace();
                            }
                        break;
                        case 15:
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
                                    filterCriterias.put(pair[0],pair[1]);
                                }
                                productDetail.setFiltercriterias(filterCriterias);
                                
                            } catch (Exception e) {
                                flag = false;
                                e.printStackTrace();
                            }   
                        break;
                        default:
                            break;
                    }
                    cid++;
                    rowNumber++;
                }
                if(!productDetail.getModelNumber().trim().equals("") && flag){
                    // productDetail.setSubCategoryMap(new HashMap<>());
                    // productDetail.setFiltercriterias(new HashMap<>());
                    if(freeItem!=null){
                        System.out.println("out"+freeItem.toString());
                        productDetail.setFreeItem(freeItem);
                    }
                       
                    //productDetail.setProductInformation(new HashMap<>());
                    productDetail.setProductVariants(new ArrayList<>());
                    // productDetail.setVariants(new HashMap<>());
                    list.add(productDetail);
                }
                    

            }   
            
        } catch (Exception e) {
            e.printStackTrace();            
        }
        return list;

    }

    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public ResponseMessage responseMessage;

    public boolean addFactorsAffected(InputStream is){
        try {
            XSSFWorkbook workbook =  new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Factors");
            int rowNumber=0;

            DataFormatter formatter = new DataFormatter();
            String value;
            PictureData pict;
            byte[] data;
            URL imageUrl;
            String fileName;
            MultipartFile multipartFile;
            BufferedImage image;
            ByteArrayOutputStream byteArrayOutputStream;
            Iterator<Row> iterator = sheet.iterator();
            ArrayList<ProductDetail> arrayList = new ArrayList<>();
            ProductDetail productDetail = null;
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                productDetail=null;
                List<ProductVariants> productVariants = new ArrayList<ProductVariants>();
                ProductVariants obj = new ProductVariants();
                List<Factors> list = new ArrayList<>();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    Factors factors = new Factors();
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("")) break;
                            productDetail = productDetailsDao.findProductDetailBymodelNumber(value);
                            productVariants =  productDetail.getProductVariants();
                            break;
                        case 1:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("")) break;
                            for(int i=0;i<productVariants.size();i++){
                                if(productVariants.get(i).getFactorName().equals(value)){
                                    productVariants.remove(productVariants.get(i));
                                }
                            }
                            obj.setFactorName(value);
                            break;
                        case 2:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productName");
                            factors.setFactorValueNonImg(value);
                            list.add(factors);
                            break;
                        case 3:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productImage1");
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            // .setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            factors.setFactorValueImg(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            list.add(factors);
                            break;
                        case 4:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productImage2");
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            // .setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            factors.setFactorValueImg(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            list.add(factors);
                            break;
                        case 5:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productImage3");
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            // .setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            factors.setFactorValueImg(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            list.add(factors);
                            break;
                        case 6:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productImage4");
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            // .setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            factors.setFactorValueImg(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            list.add(factors);
                            break;

                        case 7:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productImage5");
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            // .setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            factors.setFactorValueImg(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            list.add(factors);
                            break;
                        case 8:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("productPrice");
                            factors.setFactorValueNonImg(value);
                            list.add(factors);
                            break;
                        case 9:
                            if(productDetail==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value);
                            if(value.trim().equals("")) break;
                            factors.setFactorname("OfferPrice");
                            factors.setFactorValueNonImg(value);
                            list.add(factors);
                            break;
                        default:
                            break;

                    }
                    cid++;
                }
                rowNumber++;
                // System.out.println("list="+list);
                obj.setFactorsAffected(list);
                productVariants.add(obj);
                System.out.println(obj.getFactorName()+"\n"+obj.getFactorsAffected());
                // System.out.println("productVariants="+productVariants);
                if(productDetail!=null){
                    productDetail.setProductVariants(productVariants);
                    productDetailsDao.save(productDetail);
                }
                
                // productDetailsDao.save(productDetail);               
            }
            
            
            // if(productDetail!=null)
            //     productDetailsDao.save(productDetail);
            System.out.println("After product details save to dao");
            // responseMessage.setMessage("Product variant factors added successfully");
            return true;

            
        } catch (Exception e) {
            e.printStackTrace();
            // responseMessage.setMessage(e.getMessage());
            return false;
        }
    }

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
                            if(value.trim().equals("")) break;
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
                            if(value.trim().equals("")) break;
                            imageUrl = new URL(value);
                            System.out.println("imageUrl="+imageUrl);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            categoriesToDisplay.setCategory_image(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                        break;
                        case 2:
                            if(categoriesToDisplay==null) break;
                            value = formatter.formatCellValue(cell);
                            System.out.println(value); //subCat  name
                            if(value.trim().equals("")) break;
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
                            value = formatter.formatCellValue(cell);
                            if(value.trim().equals("")){
                                filterCriterias = null;
                                break;
                            };
                            filterCriterias = new FilterCriterias();
                            filterCriterias.setCategory(value);
                        break;
                        case 1:
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
                                    list_values.add(list[j]);
                                }
                                map.put(keyValue[0],list_values);
                            }
                            filterCriterias.setFilterCriterias(map);
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
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            
                            offerPosters.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
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
            shopByBrandsDao.deleteAll();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Shop By Brands");
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
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                ShopByBrands shopByBrands = new ShopByBrands();
                ArrayList<BrandOfferPoster> brandOfferPosters = new ArrayList<>();
                ArrayList<BrandCategory> brandCategories = new ArrayList<>();
                ArrayList<String> videoLinks = new ArrayList<>();
                while(cells.hasNext()){
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cells.next());
                            if(value.trim().equals("")){
                                shopByBrands = null;
                                break;
                            
                            }
                            System.out.println(value);
                            ShopByBrands existingShopByBrands = shopByBrandsDao.findBybrandName(value);
                            if(existingShopByBrands==null){
                                shopByBrands = new ShopByBrands();
                                
                            }else{
                                shopByBrands = existingShopByBrands;
                                brandOfferPosters = shopByBrands.getBrandOfferPosters();
                                brandCategories = shopByBrands.getBrandCategories();
                                videoLinks = shopByBrands.getVideoLinks();
                            }
                            
                            shopByBrands.setBrandName(value);
                        break;
                        case 1:
                            value = formatter.formatCellValue(cells.next());
                            if(shopByBrands==null || value.trim().equals("")) break;
                            System.out.println(value);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            shopByBrands.setBrandLogo(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                        break;
                        case 2:
                            value = formatter.formatCellValue(cells.next());
                            if(shopByBrands==null || value.trim().equals("")) break;
                            System.out.println(value);
                            String[] arr = value.split("#");
                            for(int k=0;k<arr.length;k++){
                                String offerPostersModelNum[] = arr[k].split("\\[");
                                System.out.println("offerPosterUrl:"+offerPostersModelNum[0]);
                                System.out.println("offerPosterModelNum:"+offerPostersModelNum[1]);
                                BrandOfferPoster brandOfferPoster = new BrandOfferPoster();
                                imageUrl = new URL(offerPostersModelNum[0]);
                                image = ImageIO.read(imageUrl);
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                ImageIO.write(image,"jpg",byteArrayOutputStream);
                                fileName = "sample.jpg";
                                multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                                brandOfferPoster.setOfferPoster(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));

                                offerPostersModelNum[1] = offerPostersModelNum[1].substring(0,offerPostersModelNum[1].length()-1);
                                String modelNumbers[] = offerPostersModelNum[1].split(";");
                                ArrayList<String> modelNumberList = new ArrayList<>();
                                HashSet<String> categories = new HashSet<>();
                                for(int i=0;i<modelNumbers.length;i++){
                                    modelNumberList.add(modelNumbers[i]);
                                    ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumbers[i]);
                                    if(productDetail!=null){
                                        categories.add(productDetail.getCategory());
                                    }
                                }

                                ArrayList<String> categoryList = new ArrayList<>();
                                for(String cat:categories){
                                    categoryList.add(cat);
                                }
                                brandOfferPoster.setModelNumbers(modelNumberList);
                                brandOfferPoster.setCategories(categoryList);
                                brandOfferPosters.add(brandOfferPoster);
                            }
                            
                        break;
                        case 3:
                            value = formatter.formatCellValue(cells.next());
                            if(shopByBrands==null || value.trim().equals("")) break;
                            System.out.println(value);
                            String categoriesArray[] = value.split("#");
                            for(int i=0;i<categoriesArray.length;i++){
                                System.out.println("categoryModelNums:"+categoriesArray[i]);
                                String categoryModelNum[] = categoriesArray[i].split("\\[");
                                BrandCategory brandCategory = new BrandCategory();
                                brandCategory.setCategory(categoryModelNum[0]);
                                categoryModelNum[1] = categoryModelNum[1].substring(0,categoryModelNum[1].length()-1);
                                String modelNums[] = categoryModelNum[1].split(";");
                                ArrayList<String> modelNumberList2 = new ArrayList<>();
                                for(int j=0;j<modelNums.length;j++){
                                    modelNumberList2.add(modelNums[j]);
                                }
                                brandCategory.setModelNumbers(modelNumberList2);
                                brandCategories.add(brandCategory);
                            }
                        break;
                        case 4:
                            value = formatter.formatCellValue(cells.next());
                            if(shopByBrands==null || value.trim().equals("")) break;
                            System.out.println(value);
                            String videoLinksArray[] = value.split("#");
                            for(int i=0;i<videoLinksArray.length;i++){
                                System.out.println("videoLinks:"+videoLinksArray[i]);
                                videoLinks.add(videoLinksArray[i]);
                            }
                            shopByBrands.setBrandCategories(brandCategories);
                            shopByBrands.setBrandOfferPosters(brandOfferPosters);
                            shopByBrands.setVideoLinks(videoLinks);
                            shopByBrandsDao.save(shopByBrands);
                        break;
                        default:
                        break;
                    }
                    cid++;
                }
                rowNumber++;
            
            }
            responseMessage.setMessage("Shop By Brands added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }



}