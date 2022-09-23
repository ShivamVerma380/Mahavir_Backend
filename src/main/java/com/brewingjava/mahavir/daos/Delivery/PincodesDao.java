package com.brewingjava.mahavir.daos.Delivery;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brewingjava.mahavir.entities.Delivery.Pincodes;

public interface PincodesDao extends MongoRepository<Pincodes,Integer> {
    
    public Pincodes getPincodesBypincode(int pincode);

}
