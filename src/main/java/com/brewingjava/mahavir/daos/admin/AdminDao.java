package com.brewingjava.mahavir.daos.admin;

import com.brewingjava.mahavir.entities.admin.Admin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminDao extends MongoRepository<Admin,String> {
    public Admin findByEmail(String email);     
}
