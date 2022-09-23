package com.brewingjava.mahavir.controllers.offers;

import java.util.List;

import javax.websocket.server.PathParam;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.offers.OfferPosterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class OfferPosterController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public OfferPosterService offerPosterService;



    @PostMapping("/add-offers")
    public ResponseEntity<?> addOffer(@RequestHeader("Authorization") String authorization, @RequestParam("offerPoster") String imageUrl,@RequestParam("category") String category,@RequestParam("modelNumber") List<String> modelNumber, @RequestParam("isMegaPoster") String isMegaPoster) {
        try{
            // return offerPosterService.addOffer(multipartFile,modelNumber,offerType,offerPrice,category);
            // for(int i=0;i<modelNumber.size();i++){
            //     System.out.println(modelNumber.get(i));
            // }
            return offerPosterService.addOffer(authorization,imageUrl, modelNumber, category,isMegaPoster);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    
    @GetMapping("/get-offers")
    public ResponseEntity<?> getOffers(){
        try{
            return offerPosterService.getOffers();
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-offers-by-category/{category}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getOffersByCategory(@PathVariable("category") String category){
        try{
            return offerPosterService.getOffersByCategory(category);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
