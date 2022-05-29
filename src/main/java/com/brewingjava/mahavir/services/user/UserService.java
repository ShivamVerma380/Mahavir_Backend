package com.brewingjava.mahavir.services.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.brewingjava.mahavir.config.MySecurityConfig;
import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.daos.user.OrdersDao;
import com.brewingjava.mahavir.daos.user.UserDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.user.Orders;
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
import org.springframework.stereotype.Component;

import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;

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

    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired
    public OrdersDao ordersDao;

    public ResponseEntity<?> addUser(String email,String password,String firstName,String lastName,String phoneNo){
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
            UserRequest new_user = new UserRequest(email, encoded_password, firstName, lastName, phoneNo);
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

    public ResponseEntity<?> buyProduct(String Authorization, String BuyDate, String DeliveryDate, String ProductName) {
        try {
            String token = Authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("Admin can't buy a product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailByproductName(ProductName);
            if (productDetail!=null) {
                Orders orders = new Orders();
                //orders.setOrderId("1234");
                orders.setBuyDate(BuyDate);
                orders.setBuyerEmail(email);
                orders.setDateOfDelivery(DeliveryDate);
                orders.setProductImage(productDetail.getProductImage1());
                orders.setProductName(ProductName);
                orders.setProductPrice(productDetail.getProductPrice());
                ordersDao.save(orders);
                  
                List<Orders> userOrders = userRequest.getProductsBoughtByUser();
                if (userOrders==null) {
                    List<Orders> newOrders = new ArrayList<>();
                    newOrders.add(orders);
                    userRequest.setProductsBoughtByUser(newOrders);
                } else {
                    userOrders.add(orders);
                    userRequest.setProductsBoughtByUser(userOrders);
                }
                userDao.save(userRequest);
                responseMessage.setMessage("Order saved successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                
            } else {
                responseMessage.setMessage("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);

        }
    }


    public ResponseEntity<?> loginUser(String email,String password){
        try {
            UserRequest fetch_user = userDao.findByEmail(email);
            if(fetch_user!=null){
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                
                
                if(bCryptPasswordEncoder.matches(password,fetch_user.getPassword())){
                    responseMessage.setMessage("User Logged In Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }
                responseMessage.setMessage("Bad Credentials");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }else{
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage("User logged in successfully");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
        
    }

    public ResponseEntity<?> addToCart(String authorization,String productName){
        try {
            String token = authorization.substring(7);  //"Bearer djfhfh"
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest!=null){
                ProductDetail productDetail = productDetailsDao.findProductDetailByproductName(productName);
                if(productDetail==null){
                    responseMessage.setMessage("Product does not exist");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                }
                HashSet<String> cartList = userRequest.getUserCartProducts();
                if(cartList==null){
                    HashSet<String> updatedCartList = new HashSet<>();
                    updatedCartList.add(productName);
                    userRequest.setUserCartProducts(updatedCartList);
                    userDao.save(userRequest);
                    responseMessage.setMessage("Item added to Cart successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }else{
                    //Item already present in cart
                    if(cartList.contains(productName)){
                        responseMessage.setMessage("Item is already present in cart");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                    }
                    cartList.add(productName);
                    userRequest.setUserCartProducts(cartList);
                    userDao.save(userRequest);
                    responseMessage.setMessage("Item added to cart successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }   

            }else{
                responseMessage.setMessage("Admin cannot add a product to cart");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
