package com.brewingjava.mahavir.controllers;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addAdmin(@RequestParam("email") String email,@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("phoneNo")String phoneNo){
        try {
            return adminService.addAdmin(email, name, password, phoneNo);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
