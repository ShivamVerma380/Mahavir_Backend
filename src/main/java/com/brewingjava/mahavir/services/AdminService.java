package com.brewingjava.mahavir.services;

import com.brewingjava.mahavir.daos.AdminDao;
import com.brewingjava.mahavir.daos.UserDao;
import com.brewingjava.mahavir.entities.Admin;
import com.brewingjava.mahavir.entities.User;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AdminService {
    
    @Autowired
    public AdminDao adminDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public User user;


    @Autowired
    public ResponseMessage responseMessage;

    public ResponseEntity<?> addAdmin(String email,String name, String password,String phoneNo){

        try {
            //Check email id in user account
            user = userDao.findByEmail(email);
            if(user!=null){
                responseMessage.setMessage("This email already exists with user role");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            //check email id in admin account
            Admin admin = adminDao.findByEmail(email);
            if(admin!=null){
                responseMessage.setMessage("An account already exists with this email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            //Now you can add the admin
            Admin new_admin = new Admin(email, name, password, phoneNo);
            adminDao.save(new_admin);
            responseMessage.setMessage("Admin registered successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }
}
