package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.Admin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminDao extends MongoRepository<Admin,String> {
    public Admin findByEmail(String email);     
}
