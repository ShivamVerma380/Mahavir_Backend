package com.brewingjava.mahavir.services.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import com.brewingjava.mahavir.config.MySecurityConfig;
import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.categories.CategoriesToDisplayDao;
import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.daos.user.OrdersDao;
import com.brewingjava.mahavir.daos.user.UserDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.categories.CategoriesToDisplay;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.entities.user.Orders;
import com.brewingjava.mahavir.entities.user.UserAddress;
import com.brewingjava.mahavir.entities.user.UserRequest;
import com.brewingjava.mahavir.helper.JwtResponse;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Add;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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


    @Autowired
    public CategoriesToDisplayDao categoriesToDisplayDao;
    
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
            new_user.setProductsBoughtByUser(new ArrayList<>());
            new_user.setUserCartProducts(new HashSet<>());
            new_user.setAddToCompare(new ArrayList<>());
            this.userDao.save(new_user);

            //spring security
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            //User is authenticated successfully
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);
           
            jwtResponse.setToken(token);
            new_user.setToken(token);
            this.userDao.save(new_user);

            jwtResponse.setMessage("User registered  successfully");
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addAddress(String authorization, String name,String pincode,String locality,String address,String city,String state,String landmark,String alternateMobile,String addressType){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User Not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ArrayList<UserAddress> list = userRequest.getUserAdresses();
            if(list ==null){
                list = new ArrayList<>();
            }
            if(list.size()==3){
                responseMessage.setMessage("More addresses can't be saved");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            UserAddress userAddress = new UserAddress();
            userAddress.setName(name);
            userAddress.setState(state);
            userAddress.setAddress(address);
            userAddress.setAddressType(addressType);
            if(!alternateMobile.trim().isEmpty()){
                userAddress.setAlternateMobile(alternateMobile);
            }
            if(!landmark.trim().isEmpty()){
                userAddress.setLandmark(landmark);
            }
            userAddress.setMobileNumber(userRequest.getPhoneNo());
            userAddress.setPincode(pincode);
            userAddress.setLocality(locality);
            userAddress.setCity(city);
            
            list.add(userAddress);
            userRequest.setUserAdresses(list);
            userDao.save(userRequest);

            responseMessage.setMessage("Address saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> buyProduct(String Authorization, String BuyDate, String DeliveryDate, String modelNumber) {
        try {
            String token = Authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("Admin can't buy a product");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if (productDetail!=null) {
                Orders orders = new Orders();
                //orders.setOrderId("1234");
                orders.setBuyDate(BuyDate);
                orders.setBuyerEmail(email);
                orders.setDateOfDelivery(DeliveryDate);
                orders.setProductImage(productDetail.getProductImage1());
                orders.setmodelNumber(modelNumber);
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

    public ResponseEntity<?> getBoughtProducts(String authorization){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            if(userRequest.getProductsBoughtByUser()==null){
                responseMessage.setMessage("No products buyed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            responseMessage.setMessage("My Orders");
            return ResponseEntity.status(HttpStatus.OK).body(userRequest.getProductsBoughtByUser());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    public ResponseEntity<?> loginUser(String email,String password){
        try {
            
            UserRequest fetch_user = userDao.findByEmail(email);
            if(fetch_user!=null){
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                
                
                if(bCryptPasswordEncoder.matches(password,fetch_user.getPassword())){
                    jwtResponse.setMessage(fetch_user.getFirstName()+" "+fetch_user.getLastName());
                    //String token = fetch_user.getToken();

                    jwtResponse.setToken(fetch_user.getToken());
                    return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
                }
                responseMessage.setMessage("Bad Credentials");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }else{
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
        
    }

    public ResponseEntity<?> addToCart(String authorization,String modelNumber){
        try {
            String token = authorization.substring(7);  //"Bearer djfhfh"
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest!=null){
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
                if(productDetail==null){
                    responseMessage.setMessage("Product does not exist");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
                }
                HashSet<String> cartList = userRequest.getUserCartProducts();
                if(cartList==null){
                    HashSet<String> updatedCartList = new HashSet<>();
                    updatedCartList.add(modelNumber);
                    userRequest.setUserCartProducts(updatedCartList);
                    userDao.save(userRequest);
                    responseMessage.setMessage("Item added to Cart successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }else{
                    //Item already present in cart
                    if(cartList.contains(modelNumber)){
                        responseMessage.setMessage("Item is already present in cart");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                    }
                    cartList.add(modelNumber);
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

    public ResponseEntity<?> getCartDetails(String authorization) {
        try{
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest user = userDao.findByEmail(email);
            if(user == null){
                responseMessage.setMessage("You cannot view cart details");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            HashSet<String> cartDetails = user.getUserCartProducts();
            if(cartDetails==null){
                responseMessage.setMessage("Cart Empty");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            List<ProductDetail> products = new ArrayList<>();
            for(String modelNummber : cartDetails){
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNummber);
                products.add(productDetail);
            }
            responseMessage.setMessage("Cart Details");
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
        catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getOrders(String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest user = userDao.findByEmail(email);
            if(user == null){
                responseMessage.setMessage("You cannot view orders");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<Orders> orders = user.getProductsBoughtByUser();
            if(orders==null){
                responseMessage.setMessage("No orders placed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            responseMessage.setMessage("Orders");
            return ResponseEntity.status(HttpStatus.OK).body(orders);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addToCompare(String authorization,String category,String modelNumber){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("Only user can compare products");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
            if(productDetail==null){
                responseMessage.setMessage("Product Does Not exist");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            
            List<String> addToCompare = userRequest.getAddToCompare();
            if(addToCompare.size()==4){
                responseMessage.setMessage("You can compare only 4 products at a time");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            if(!addToCompare.isEmpty()){
                ListIterator<String> listIterator = addToCompare.listIterator();
                while(listIterator.hasNext()){
                    
                    if(listIterator.next().equals(modelNumber)){
                        responseMessage.setMessage("Product already added to compare");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                    }

                    
                    if(!productDetail.getCategory().equals(category)){
                    responseMessage.setMessage("You can compare similar products only");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
                    }                
                    
                }
            }
            

            
            addToCompare.add(modelNumber);
            userRequest.setAddToCompare(addToCompare);
            userDao.save(userRequest);
            responseMessage.setMessage("Product added To Compare");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);


        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getUserAddToCompare(String authorization){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            return ResponseEntity.ok(userRequest.getAddToCompare());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
