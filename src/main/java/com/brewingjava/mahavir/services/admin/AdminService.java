package com.brewingjava.mahavir.services.admin;

import com.brewingjava.mahavir.config.MySecurityConfig;
import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.user.UserDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.user.UserRequest;
import com.brewingjava.mahavir.helper.JwtResponse;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AdminService {
    
    @Autowired
    public AdminDao adminDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public UserRequest user;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtResponse jwtResponse;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

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
            String encoded_password = mySecurityConfig.passwordEncoder().encode(password);
            Admin new_admin = new Admin(email, name, encoded_password, phoneNo);
            adminDao.save(new_admin);
            
            //spring security
            System.out.println("Before authentication manager");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            System.out.println("After authentication manager");
            //Admin is authenticated successfully
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(new_admin.getEmail());
            String token = jwtUtil.generateToken(userDetails);


            jwtResponse.setMessage("Admin registered successfully");
            jwtResponse.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }

    public ResponseEntity<?> loginAdmin(String email, String password) {
        try {
            Admin admin = adminDao.findByEmail(email);

            if(admin==null){
                responseMessage.setMessage("No admin account exists with this email");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            //spring security
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if(!bCryptPasswordEncoder.matches(password, admin.getPassword())){
                responseMessage.setMessage("Password is incorrect");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);
            // String token = jwtUtil.generateToken(userDetails);

            jwtResponse.setMessage("Admin logged in successfully");

            jwtResponse.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
            
            // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

            //Admin is authenticated successfully



        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
