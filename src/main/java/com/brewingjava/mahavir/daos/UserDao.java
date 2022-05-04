package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserDao extends MongoRepository<User,String> {
    
    public User findByEmail(String email);
}
