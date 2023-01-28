package com.brewingjava.mahavir.controllers.admin;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.admin.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public AdminService adminService;

    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestParam("Email") String email,@RequestParam("Name")String name,@RequestParam("Password")String password,@RequestParam("PhoneNo")String phoneNo){
        try {
            return adminService.addAdmin(email, name, password, phoneNo);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@RequestParam("Email") String email,@RequestParam("Password")String password){
        try {
            return adminService.loginAdmin(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
