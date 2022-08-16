package com.brewingjava.mahavir.services.offers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.offers.OfferPosterDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.offers.OfferPosters;
import com.brewingjava.mahavir.entities.offers.OfferTypeDetails;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OfferPosterService{

    @Autowired
    public OfferPosterDao offerPosterDao;

    @Autowired
    public OfferPosters offerPosters;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public OfferTypeDetails offerPricePercentage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public AdminDao adminDao;
    

    public ResponseEntity<?> addOffer(String authorization,String imageUrl, List<String> modelNumber, String category,String isMegaPoster){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Admin admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admins can add offers");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            offerPosters.setImageUrl(imageUrl);
            // offerPosters.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            HashSet<String> hashSet = new HashSet<>();
            for(int i=0;i<modelNumber.size();i++){
                try {
                    ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber.get(i));
                    if(productDetail!=null){
                        hashSet.add(modelNumber.get(i));
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            offerPosters.setCategory(category);
            offerPosters.setModelNumbers(new ArrayList<>(hashSet));
            offerPosters.setIsMegaPoster(isMegaPoster);
            offerPosterDao.save(offerPosters);
            responseMessage.setMessage("Offer saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getOffersByCategory(String category) {
        try {
            List<OfferPosters> offers = offerPosterDao.findOffersByCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body(offers);
        }  catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> getOffers(){
        try {
            List<OfferPosters> offerPosters = offerPosterDao.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(offerPosters);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
