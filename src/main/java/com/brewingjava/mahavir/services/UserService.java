package com.brewingjava.mahavir.services;

import  com.brewingjava.mahavir.daos.UserDao;
import com.brewingjava.mahavir.entities.User;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public User user;

    @Autowired
    public UserDao userDao;

    public ResponseEntity<?> addUser(String email,String name,String address,String phoneNo,String city,String state,String pinCode){
        try {
            user = userDao.findByEmail(email);
            if(user!=null){
                responseMessage.setMessage("An account already exists with this email");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            user.setEmail(email);
            user.setAddress(address);
            user.setCity(city);
            user.setName(name);
            user.setPhoneNo(phoneNo);
            user.setPinCode(pinCode);
            user.setState(state);

            this.userDao.save(user);
            
            responseMessage.setMessage("User added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
