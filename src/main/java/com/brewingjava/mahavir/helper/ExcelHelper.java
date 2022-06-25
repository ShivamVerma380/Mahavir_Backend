package com.brewingjava.mahavir.helper;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.product.FreeItem;
import com.brewingjava.mahavir.entities.product.ProductDetail;

import lombok.val;

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
            XSSFSheet sheet = workbook.getSheet("Excel automation");
            int rowNumber=0;

            Iterator<Row> iterator = sheet.iterator();
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

                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            // int s = (int)cell.getNumericCellValue();
                            value = formatter.formatCellValue(cell);
                            productDetail.setModelNumber(value);
                            break;
                        case 1:
                            value = formatter.formatCellValue(cell);
                            productDetail.setProductName(value);
                            break;
                        
                        
                        case 2:
                            
                            value = formatter.formatCellValue(cell);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage1(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            break;
                        case 3:
                            value = formatter.formatCellValue(cell);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage2(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            break;
                        case 4:
                            value = formatter.formatCellValue(cell);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage3(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            break;
                        case 5:
                            value = formatter.formatCellValue(cell);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage4(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                            break;
                        case 6:
                            value = formatter.formatCellValue(cell);
                            imageUrl = new URL(value);
                            image = ImageIO.read(imageUrl);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image,"jpg",byteArrayOutputStream);
                            fileName = "sample.jpg";
                            multipartFile = new MockMultipartFile(fileName,fileName,"jpg",byteArrayOutputStream.toByteArray());
                            productDetail.setProductImage5(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes())); 
                            break;
                        
                        case 7:
                            value = formatter.formatCellValue(cell);
                            productDetail.setProductPrice(value);
                            break;
                        case 8:
                            value = formatter.formatCellValue(cell);
                            productDetail.setOfferPrice(value);
                            break;
                        case 9:
                            value = formatter.formatCellValue(cell);
                            productDetail.setCategory(value);
                            break;
                        case 10:
                            value = formatter.formatCellValue(cell);
                            productDetail.setProductHighlights(value);
                            break;
                        default:
                            break;
                            
                    }
                    cid++;
                    rowNumber++;
                }
                if(!productDetail.getModelNumber().trim().equals("")){
                    productDetail.setSubCategoryMap(new HashMap<>());
                    productDetail.setFiltercriterias(new HashMap<>());
                    productDetail.setFreeItem(new FreeItem());
                    productDetail.setProductInformation(new HashMap<>());
                    productDetail.setProductVariants(new ArrayList<>());
                    productDetail.setVariants(new HashMap<>());
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

    public List<ProductDetail> addSubCategories(InputStream is){

        List<ProductDetail> list = new ArrayList<>();
        
        DataFormatter formatter = new DataFormatter();
        String value;
        try {
            XSSFWorkbook workbook =  new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("SubCategories");
            int rowNumber=0;

            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber<=1){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                ProductDetail productDetail = null;
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){
                        case 0:
                            value = formatter.formatCellValue(cell);
                            productDetail = productDetailsDao.findProductDetailBymodelNumber(value);
                            break;
                        case 1:
                            value = formatter.formatCellValue(cell);
                            String arr[]= value.split(",");
                            if(productDetail!=null){
                                HashMap<String,String> map = new HashMap<>();
                                for(int i=0;i<arr.length;i++){
                                    String subCat[] = arr[i].split(":");
                                    map.put(subCat[0],subCat[1]);
                                }
                                productDetail.setSubCategoryMap(map);
                                list.add(productDetail);
                            }
                            break;
                        default:
                            break;
                            
                    }
                    cid++;
                    rowNumber++;
                }
            
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}