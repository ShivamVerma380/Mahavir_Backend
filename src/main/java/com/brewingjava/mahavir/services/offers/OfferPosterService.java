package com.brewingjava.mahavir.services.offers;

import java.util.List;

import com.brewingjava.mahavir.daos.offers.OfferPosterDao;
import com.brewingjava.mahavir.entities.offers.OfferPosters;
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
    



    public ResponseEntity<?> addImage(MultipartFile multipartFile){
        try{
            offerPosters.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            offerPosterDao.save(offerPosters);
            responseMessage.setMessage("Image added successfully");
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