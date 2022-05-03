package com.brewingjava.mahavir.controllers;

import com.brewingjava.mahavir.services.EmailVerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailVerification {
    
    @Autowired
    public EmailVerificationService emailVerificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<?> getOTP(@RequestParam("email")String email){
        try {
            return emailVerificationService.getOtp(email); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
