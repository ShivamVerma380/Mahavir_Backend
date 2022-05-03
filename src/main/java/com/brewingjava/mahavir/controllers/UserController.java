package com.brewingjava.mahavir.controllers;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("address") String address,@RequestParam("phoneNo")String phoneNo,@RequestParam("city")String city,@RequestParam("state")String state,@RequestParam("pincode")String pinCode){
        try {
            return userService.addUser(email, name, address, phoneNo, city, state, pinCode);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


}
