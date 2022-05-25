package com.brewingjava.mahavir.controllers;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.OfferPosterService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class OfferPosterController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public OfferPosterService offerPosterService;



    @PostMapping("/add-offer")
    public ResponseEntity<?> addOffer(@RequestParam("offer") MultipartFile multipartFile){
        try{
            return offerPosterService.addImage(multipartFile);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/getoffers")
    public ResponseEntity<?> getOffers(){
        try{
            return offerPosterService.getOffers();
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
