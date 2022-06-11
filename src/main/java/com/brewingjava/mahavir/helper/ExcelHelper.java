package com.brewingjava.mahavir.helper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

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
        PictureData pict;
        byte[] data;

        try {
            XSSFWorkbook workbook =  new XSSFWorkbook(is);
            List<XSSFPictureData> pictures= workbook.getAllPictures();
            XSSFSheet sheet = workbook.getSheet("Excel automation");
            int rowNumber=0;

            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber==0){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid=0;
                ProductDetail productDetail = new ProductDetail();
                while(cells.hasNext()){
                    Cell cell = cells.next();

                    switch(cid){
                        case 0:
                            // int s = (int)cell.getNumericCellValue();
                            productDetail.setModelNumber(Double.toString(cell.getNumericCellValue()));
                            break;
                        case 1:
                            productDetail.setProductName(cell.getStringCellValue());
                            break;
                        
                        
                        case 2:
                            // pict = pictures.get(0);
                            //String ext = pict.g
                            data = pictures.get(0).getData();
                            // try{
                            //     OutputStream out = new FileOutputStream("pict.jpg");
                            //     out.write(data);
                            
                            // }catch(Exception e){
                            //     e.printStackTrace();
                            // }
                            productDetail.setProductImage1(new Binary(BsonBinarySubType.BINARY, data));
                            break;
                        case 3:
                            // //continue;
                            // pict = (PictureData)cell;
                            // //String ext = pict.g
                            // data = pict.getData();
                            // pict = pictures.get(0);
                            //String ext = pict.g
                            data = pictures.get(0).getData();
                            // try{
                            //     OutputStream out = new FileOutputStream("pict.jpg");
                            //     out.write(data);
                            
                            // }catch(Exception e){
                            //     e.printStackTrace();
                            // }
                        productDetail.setProductImage2(new Binary(BsonBinarySubType.BINARY, data));
                        break;
                        case 4:
                        // pict = (PictureData)cell;
                        // //String ext = pict.g
                        // data = pict.getData();
                        // pict = pictures.get(0);
                            //String ext = pict.g
                            data = pictures.get(0).getData();
                        // try{
                        //     OutputStream out = new FileOutputStream("pict.jpg");
                        //     out.write(data);
                        
                        // }catch(Exception e){
                        //     e.printStackTrace();
                        // }
                        productDetail.setProductImage3(new Binary(BsonBinarySubType.BINARY, data));
                        break;
                        case 5:
                        // pict = (PictureData)cell;
                        // //String ext = pict.g
                        // data = pict.getData();
                            // pict = pictures.get(0);
                            //String ext = pict.g
                            data = pictures.get(0).getData();
                        // try{
                        //     OutputStream out = new FileOutputStream("pict.jpg");
                        //     out.write(data);
                        
                        // }catch(Exception e){
                        //     e.printStackTrace();
                        // }
                        productDetail.setProductImage4(new Binary(BsonBinarySubType.BINARY, data));
                        break;
                        case 6:
                        // pict = (PictureData)cell;
                        // //String ext = pict.g
                        // data = pict.getData();
                        // pict = pictures.get(0);
                            //String ext = pict.g
                            data = pictures.get(0).getData();
                        // try{
                        //     OutputStream out = new FileOutputStream("pict.jpg");
                        //     out.write(data);
                        
                        // }catch(Exception e){
                        //     e.printStackTrace();
                        // }
                        productDetail.setProductImage5(new Binary(BsonBinarySubType.BINARY, data));
                        break;
                        
                        case 7:
                            productDetail.setProductPrice(Double.toString(cell.getNumericCellValue()));
                            break;
                        case 8:
                            productDetail.setOfferPrice(Double.toString(cell.getNumericCellValue()));
                            break;
                        case 9:
                            productDetail.setCategory(cell.getStringCellValue());
                            break;
                        case 10:
                            productDetail.setProductHighlights(cell.getStringCellValue());
                            break;
                        default:
                            break;
                            
                    }
                    cid++;
                    rowNumber++;
                }
                list.add(productDetail);

            }   
            
        } catch (Exception e) {
            e.printStackTrace();            
        }
        return list;

    }
    
}
