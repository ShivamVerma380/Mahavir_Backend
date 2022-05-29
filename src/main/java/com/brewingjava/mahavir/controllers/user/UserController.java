package com.brewingjava.mahavir.controllers.user;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestParam("Email") String email,@RequestParam("Password") String password, @RequestParam("first_name") String firstName,@RequestParam("last_name") String lastName,@RequestParam("PhoneNo")String phoneNo){
        try {
            return userService.addUser(email, password, firstName, lastName, phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/buy-product")
    public ResponseEntity<?> buyproduct(@RequestHeader("Authorization") String Authorization, @RequestParam("Buy_Date") String BuyDate, @RequestParam("Delivery_Date") String DeliveryDate, @RequestParam("Product_Name") String ProductName) {
        try {
            return userService.buyProduct(Authorization, BuyDate, DeliveryDate, ProductName);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization")String authorization,@RequestParam("ProductName")String productName){
        try {
            return userService.addToCart(authorization, productName);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> userLogin( @RequestParam("Email") String email,@RequestParam("Password") String password){
        try {
            return userService.loginUser(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }


}
