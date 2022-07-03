package com.brewingjava.mahavir.services.HomepageComponents;

import java.util.ArrayList;
import java.util.HashSet;

import org.etsi.uri.x01903.v13.ResponderIDType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.daos.HomepageComponents.DealsDao;
import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.HomepageComponents.Deals;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ProductsDetailsResponse;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Component
public class DealsService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public DealsDao dealsDao;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public ProductDetailsDao productDetailsDao;

    public ResponseEntity<?>addDeals(String authorization,String title,HashSet<String> modelNumbers){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add deals");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            Deals deals = new Deals();
            deals.setTitle(title);
            HashSet<String> mNumsFinal = new HashSet<>();
            HashSet<String> categories = new HashSet<>();
            for(String modelNum:modelNumbers){
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNum);
                if(productDetail!=null){
                    categories.add(productDetail.getCategory());
                    mNumsFinal.add(modelNum);
                }
            }
            HashSet<ProductsDetailsResponse> products = new HashSet<>(); 
            for(String mNum:mNumsFinal){
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(mNum);
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
            }
            deals.setProducts(products);
            deals.setCategories(new ArrayList<>(categories));
            dealsDao.save(deals);
            responseMessage.setMessage("Deal added successfully");
            return ResponseEntity.ok().body(responseMessage);
        }catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getDeals(){
        try {
            return ResponseEntity.ok(dealsDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
