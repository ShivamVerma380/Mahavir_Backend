package com.brewingjava.mahavir.daos.orders;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.orders.OrderDetails;

public interface OrderDetailsDao extends MongoRepository<OrderDetails, Integer> {

    public OrderDetails getOrderDetailsByOrderId(int orderId);

}
    
