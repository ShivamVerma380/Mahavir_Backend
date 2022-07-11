package com.brewingjava.mahavir.controllers.user;

import com.brewingjava.mahavir.entities.user.UserAddress;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
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

    @PutMapping("/updatePassword/{password}")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorization,@PathVariable("password") String password){
        try {
            return userService.updatePassword(authorization, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/address")
    public ResponseEntity<?> addAddress(@RequestHeader("Authorization") String authorization,@RequestParam("name") String name,@RequestParam("pincode")String pincode,@RequestParam("locality") String locality,@RequestParam("address") String address,@RequestParam("city") String city,@RequestParam("state") String state,@RequestParam("landmark") String landmark,@RequestParam("alternateMobile") String alternateMobile,@RequestParam("addressType") String addressType ){
        try {
            return userService.addAddress(authorization, name,pincode,locality,address,city,state,landmark,alternateMobile,addressType);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @GetMapping("/address")
    public ResponseEntity<?> getAddresses(@RequestHeader("Authorization") String authorization){
        try {
            
            return userService.getAddress(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/buy-product")
    public ResponseEntity<?> buyproduct(@RequestHeader("Authorization") String Authorization, @RequestParam("Buy_Date") String BuyDate, @RequestParam("Delivery_Date") String DeliveryDate, @RequestParam("ModelNumber") String modelNumber) {
        try {
            return userService.buyProduct(Authorization, BuyDate, DeliveryDate, modelNumber);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-to-compare")
    public ResponseEntity<?> addToCompare(@RequestHeader("Authorization") String authorization,@RequestParam("Category") String category,@RequestParam("ModelNumber") String modelNumber){
        try {
            return userService.addToCompare(authorization, category, modelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    
    

    @GetMapping("/get-bought-products")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getProduct(@RequestHeader("Authorization")String authorization){
        try {
             return userService.getBoughtProducts(authorization);  
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/add-to-cart")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization")String authorization,@RequestParam("modelNumber")String modelNumber){
        try {
            return userService.addToCart(authorization, modelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    @GetMapping("/get-cart-details")
    public ResponseEntity<?> getCartDetails(@RequestHeader("Authorization") String authorization){
        try {
            return userService.getCartDetails(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/get-orders")
    public ResponseEntity<?> getOrders(@RequestHeader("Authorization") String authorization){
        try {
            return userService.getOrders(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/login-user")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> userLogin( @RequestParam("Email") String email,@RequestParam("Password") String password){
        try {
            return userService.loginUser(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }


    @GetMapping("/add-to-compare")
    public ResponseEntity<?> getUserAddToCompare(@RequestHeader("Authorization") String authorization){
        try {
            return userService.getUserAddToCompare(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> addToWishlist(@RequestHeader("Authorization") String authorization,@RequestParam("modelNumber") String modelNumber){
        try {
            return userService.addToWishlist(authorization, modelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlist(@RequestHeader("Authorization") String authorization){
        try {
            return userService.getWishlist(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @DeleteMapping("/wishlist/{modelNumber}")
    public ResponseEntity<?> deleteWishlist(@RequestHeader("Authorization") String authorization,@PathVariable("modelNumber") String modelNumber){
        try {
            return userService.deleteWishlist(authorization, modelNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


}
