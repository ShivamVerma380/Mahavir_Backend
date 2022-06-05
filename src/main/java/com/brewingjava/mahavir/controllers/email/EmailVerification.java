package com.brewingjava.mahavir.controllers.email;

import com.brewingjava.mahavir.services.email.EmailVerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class EmailVerification {
    
    @Autowired
    public EmailVerificationService emailVerificationService;

    @GetMapping("/verify-email/{email}")
    public ResponseEntity<?> getOTP(@PathVariable("email")String email){
        try {
            return emailVerificationService.getOtp(email); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
    
}
