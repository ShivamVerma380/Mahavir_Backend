package com.brewingjava.mahavir.helper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.entities.test.ProductDetail;

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
                String value;
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
                            productDetail.setImg1(value);
                            break;
                        case 3:
                            value = formatter.formatCellValue(cell);
                            productDetail.setImg2(value);
                            break;
                        case 4:
                            value = formatter.formatCellValue(cell);
                            productDetail.setImg3(value);
                            break;
                        case 5:
                            value = formatter.formatCellValue(cell);
                            productDetail.setImg4(value);
                            break;
                        case 6:
                            value = formatter.formatCellValue(cell);
                            productDetail.setImg5(value); 
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
                list.add(productDetail);

            }   
            
        } catch (Exception e) {
            e.printStackTrace();            
        }
        return list;

    }
    
}
