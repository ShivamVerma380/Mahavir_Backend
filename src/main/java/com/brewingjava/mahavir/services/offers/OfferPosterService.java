package com.brewingjava.mahavir.services.offers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.brewingjava.mahavir.daos.offers.OfferPosterDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.offers.OfferPosters;
import com.brewingjava.mahavir.entities.offers.OfferTypeDetails;
import com.brewingjava.mahavir.entities.product.ProductDetail;
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

    public ResponseEntity<?> addOffer(MultipartFile multipartFile, String modelNumber, String OfferType, String OfferValue){
        try{
            offerPosters.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            if(productDetailsDao.findProductDetailBymodelNumber(modelNumber)==null){
                responseMessage.setMessage("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            HashMap<String,OfferTypeDetails> offer = new HashMap<>();
            double priceNew = 0;
            if(OfferType.equalsIgnoreCase("price")){
                priceNew = Double.parseDouble(OfferValue);
            }
            else if(OfferType.equalsIgnoreCase("percentage")){
                String originalPrice = productDetailsDao.findProductDetailBymodelNumber(modelNumber).getProductPrice();
                priceNew = Double.parseDouble(originalPrice);
                priceNew = priceNew - (priceNew*Double.parseDouble(OfferValue))/100;
                
            }
            else{
                responseMessage.setMessage("Offer type not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            OfferTypeDetails offerTypeDetails = new OfferTypeDetails(OfferType,OfferValue);
            offer.put(modelNumber,offerTypeDetails);
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            System.out.println(priceNew);
            productDetail.setOfferPrice(String.valueOf(priceNew));
            productDetailsDao.save(productDetail);
            offerPosters.setOfferDetails(offer);
            offerPosterDao.save(offerPosters);
            responseMessage.setMessage("Offer added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getOffers(){
        try{
            List<OfferPosters> offers = offerPosterDao.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}