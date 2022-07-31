package com.brewingjava.mahavir.services.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.orders.OrderDetailsDao;
import com.brewingjava.mahavir.daos.user.UserDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.orders.OrderDetails;
import com.brewingjava.mahavir.entities.user.UserRequest;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.helper.orders.EmailOrder;

@Component
public class OrderDetailsService {
    
    @Autowired
    public OrderDetailsDao orderDetailsDao;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public EmailOrder emailOrder;


    @Autowired
    public UserDao userDao;

    @Autowired
    public AdminDao adminDao;
  
    public ResponseEntity<?> addOrder(String authorization,OrderDetails orderDetails){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            if(orderDetails==null){
                responseMessage.setMessage("Order not placed");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            List<OrderDetails> allOrders = orderDetailsDao.findAll();


            orderDetails.setOrderId(allOrders.size()+1);
            orderDetails.setBuyerEmail(userRequest.getEmail());
            HashMap<String,Integer> products = orderDetails.getProducts();
            HashMap<String,Boolean> isProductRated = new HashMap<>();
            for(Map.Entry<String,Integer> map:products.entrySet()){
                isProductRated.put(map.getKey(), false);
            }
            orderDetails.setIsProductRated(isProductRated);
            if(orderDetails.getUserAddress()==null){
                responseMessage.setMessage("Please provide address");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            if(orderDetails.getPaymentMode()==null || orderDetails.getPaymentAmount()==null){
                responseMessage.setMessage("Please provide payment details");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            orderDetailsDao.save(orderDetails);

            emailOrder.sendEmailToAdmin("shivam380.testing@gmail.com",orderDetails);
            emailOrder.sendEmailToUser(email, userRequest.getFirstName(), orderDetails);

            List<OrderDetails> productsBoughtByUser = userRequest.getProductsBoughtByUser();
            if(productsBoughtByUser==null){
                productsBoughtByUser = new ArrayList<>();
            }
            productsBoughtByUser.add(orderDetails);
            userRequest.setProductsBoughtByUser(productsBoughtByUser);
            userDao.save(userRequest);

            responseMessage.setMessage("Order Placed successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getOrders(String authorization){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            UserRequest userRequest = userDao.findByEmail(email);
            if(userRequest==null){
                responseMessage.setMessage("User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            } 
            List<OrderDetails> list = userRequest.getProductsBoughtByUser();
            if(list==null){
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getAllOrders(){
        try {
            return ResponseEntity.ok().body(orderDetailsDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getPendingOrders() {
        try {
            ArrayList<OrderDetails> list = (ArrayList<OrderDetails>)orderDetailsDao.findAll();
            ArrayList<OrderDetails> pendingOrders = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(!list.get(i).isOrderCompleted()){
                    pendingOrders.add(list.get(i));
                }
            }
            return ResponseEntity.ok().body(pendingOrders);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getCompletedOrders() {
        try {
            ArrayList<OrderDetails> list = (ArrayList<OrderDetails>)orderDetailsDao.findAll();
            ArrayList<OrderDetails> completedOrders = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(list.get(i).isOrderCompleted()){
                    completedOrders.add(list.get(i));
                }
            }
            return ResponseEntity.ok().body(completedOrders);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> updateOrderStatus(String authorization,int orderId,String deliveryDate) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Admin admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Admin Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            OrderDetails orderDetails = orderDetailsDao.getOrderDetailsByOrderId(orderId);
            orderDetails.setDeliveryDate(deliveryDate);
            orderDetails.setOrderCompleted(true);
            responseMessage.setMessage("Order updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    

}
