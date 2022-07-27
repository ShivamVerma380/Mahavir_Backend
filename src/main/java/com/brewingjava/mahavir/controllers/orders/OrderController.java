package com.brewingjava.mahavir.controllers.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.brewingjava.mahavir.daos.orders.OrderDetailsDao;
import com.brewingjava.mahavir.entities.orders.OrderDetails;
import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.orders.OrderDetailsService;

@RestController
public class OrderController {
    
    @Autowired
    public OrderDetailsDao orderDetailsDao;

    @Autowired
    public OrderDetails orderDetails;

    @Autowired
    public OrderDetailsService orderDetailsService;

    @Autowired
    public ResponseMessage responseMessage;

    @PostMapping("/order")
    public ResponseEntity<?> addOrder(@RequestHeader("Authorization") String authorization,@RequestBody OrderDetails orderDetails) {
        // orderDetailsDao.save(orderDetails);
        try {
            System.out.println("order\n"+orderDetails.toString());
            return orderDetailsService.addOrder(authorization, orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
        
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrders(@RequestHeader("Authorization")String authorization){
        try {
            return orderDetailsService.getOrders(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
