package com.brewingjava.mahavir.services;

import com.brewingjava.mahavir.config.MySecurityConfig;
import com.brewingjava.mahavir.daos.AdminDao;
import  com.brewingjava.mahavir.daos.UserDao;
import com.brewingjava.mahavir.entities.Admin;
import com.brewingjava.mahavir.entities.UserRequest;
import com.brewingjava.mahavir.helper.JwtResponse;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component

public class UserService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public JwtResponse jwtResponse;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    public ResponseEntity<?> addUser(String email,String password,String name,String address,String phoneNo,String city,String state,String pinCode){
        try {
            //Check email in admin db
            Admin admin = adminDao.findByEmail(email);
            if(admin!=null){
                responseMessage.setMessage("This email already exists with admin account");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            //Check email in user db
            UserRequest existingUser = userDao.findByEmail(email);
            if(existingUser!=null){
                responseMessage.setMessage("An account already exists with this email");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            //add user
            String encoded_password = mySecurityConfig.passwordEncoder().encode(password);
            UserRequest new_user = new UserRequest(email, encoded_password, name, address, phoneNo, city, state, pinCode);
            this.userDao.save(new_user);

            //spring security
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            //User is authenticated successfully
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);

            jwtResponse.setToken(token);

            jwtResponse.setMessage("User registered  successfully");
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
