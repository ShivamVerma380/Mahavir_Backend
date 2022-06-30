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
            HashSet<String> categories = new HashSet<>();
            for(String modelNum:modelNumbers){
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNum);
                if(productDetail==null){
                    modelNumbers.remove(modelNum);
                }else{
                    categories.add(productDetail.getCategory());
                }
            }
            deals.setModelNumbers(modelNumbers);
            deals.setCategories(new ArrayList<>(categories));
            dealsDao.save(deals);
            responseMessage.setMessage("Deal added successfully");
            return ResponseEntity.ok().body(responseMessage);
        }catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
