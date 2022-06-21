package com.brewingjava.mahavir.controllers.email;

import com.brewingjava.mahavir.services.email.EmailVerificationService;
import com.brewingjava.mahavir.services.email.Mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
            Random random = new Random();
            int number = random.nextInt(999999);

            String otp = String.format("%06d", number);
            
            Mail mail = new Mail();
            mail.setFrom("shivam380.testing@gmail.com");
            mail.setTo(email);
            mail.setSubject("Sending Email with Freemarker HTML Template Example");


            Map model = new HashMap();
            model.put("name", email);
            model.put("location", "Belgium");
            model.put("signature", "https://memorynotfound.com");
            model.put("otp", otp);
            mail.setModel(model);

            emailVerificationService.sendSimpleMessage(mail);

            return ResponseEntity.status(HttpStatus.OK).body("Email sent");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
    
}
