package com.brewingjava.mahavir.controllers.email;

import com.brewingjava.mahavir.helper.OtpResponse;
import com.brewingjava.mahavir.helper.ResponseMessage;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class EmailVerification {
    
    @Autowired
    public EmailVerificationService emailVerificationService;

    @Autowired
    public OtpResponse otpResponse;

    @GetMapping("/verify-email/{email}")
    public ResponseEntity<?> getOTP(@PathVariable("email")String email){
        try {
            Random random = new Random();
            int number = random.nextInt(999999);

            String otp = String.format("%06d", number);
            
            Mail mail = new Mail();
            mail.setFrom("shivam380.testing@gmail.com");
            mail.setTo(email);
            mail.setSubject("Mahavir Electronics-OTP Verification");


            Map model = new HashMap();
            model.put("name", "Customer,");
            model.put("location", "Belgium");
            model.put("signature", "https://memorynotfound.com");
            model.put("otp", otp);
            mail.setModel(model);

            emailVerificationService.sendSimpleMessage(mail);
            otpResponse.setMessage("Email sent successfully");
            otpResponse.setOtp(otp);
            return ResponseEntity.status(HttpStatus.OK).body(otpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/resend-otp/{email}")
    public ResponseEntity<?> resendOTP(@PathVariable("email") String email){
        try {
            Random random = new Random();
            int number = random.nextInt(999999);

            String otp = String.format("%06d", number);
            
            Mail mail = new Mail();
            mail.setFrom("shivam380.testing@gmail.com");
            mail.setTo(email);
            mail.setSubject("Mahavir Electronics-New OTP");


            Map model = new HashMap();
            model.put("name", "Customer,");
            model.put("location", "Belgium");
            model.put("signature", "https://memorynotfound.com");
            model.put("otp", otp);
            mail.setModel(model);

            emailVerificationService.sendSimpleMessage(mail);
            otpResponse.setMessage("Email sent successfully");
            otpResponse.setOtp(otp);
            return ResponseEntity.status(HttpStatus.OK).body(otpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email")String email){
        try {
            Random random = new Random();
            int number = random.nextInt(999999);

            String otp = String.format("%06d", number);
            
            Mail mail = new Mail();
            mail.setFrom("shivam380.testing@gmail.com");
            mail.setTo(email);
            mail.setSubject("Forgot Password-Mahavir Electronics");

            Map model = new HashMap();
            model.put("name", "Customer,");
            model.put("location", "Belgium");
            model.put("signature", "https://memorynotfound.com");
            model.put("otp", otp);
            mail.setModel(model);
            return emailVerificationService.forgotPassword(mail);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

        
    
}
