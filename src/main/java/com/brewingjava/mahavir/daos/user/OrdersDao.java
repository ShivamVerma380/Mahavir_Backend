package com.brewingjava.mahavir.daos.user;

import com.brewingjava.mahavir.entities.user.Orders;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersDao extends MongoRepository<Orders,String>{
    public Orders findOrdersByOrderId(String orderId);
    
}
