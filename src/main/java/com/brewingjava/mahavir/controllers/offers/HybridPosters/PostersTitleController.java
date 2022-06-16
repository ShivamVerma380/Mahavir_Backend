package com.brewingjava.mahavir.controllers.offers.HybridPosters;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.offers.HybridPosters.PostersTitleService;

@RestController
public class PostersTitleController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public PostersTitleService postersTitleService;
    
    @PostMapping("/add-posters/image")
    public ResponseEntity<?> addImagePosters(@RequestHeader("Authorization")String authorization,@RequestParam("title")String title, @RequestParam("image") MultipartFile image,@RequestParam("models") ArrayList<String> modelNos){
        try {
            return postersTitleService.addImagePosters(authorization, title, image, modelNos);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage); 
        }
    }

    @PostMapping("/add-posters")
    public ResponseEntity<?> addNonImagePosters(@RequestHeader("Authorization") String authorization,@RequestParam("title")String title,@RequestParam("models") ArrayList<String> modelNos){
        try {
            return postersTitleService.addNonImagePosters(authorization, title, modelNos);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @GetMapping("/get-posters")
    public ResponseEntity<?> getTitlePosters(){
        try {
            return postersTitleService.getPosters();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
