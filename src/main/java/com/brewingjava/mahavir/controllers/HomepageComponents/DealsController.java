package com.brewingjava.mahavir.controllers.HomepageComponents;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.HomepageComponents.DealsService;

@RestController
public class DealsController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public DealsService dealsService;

    @PostMapping("/deals")
    public ResponseEntity<?> addDeals(@RequestHeader("Authorization") String authorization,@RequestParam("title") String title,@RequestParam("modelNumbers") HashSet<String> modelNumbers){
        try {
            return dealsService.addDeals(authorization, title, modelNumbers);
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/deals")
    public ResponseEntity<?> getDeals(){
        try {
            return dealsService.getDeals();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
