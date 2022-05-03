package com.brewingjava.mahavir.daos;

import com.brewingjava.mahavir.entities.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableMongoRepositories
public interface UserDao extends MongoRepository<String,User> {
    
    public User findByEmail(String email);

    public User save(User user);
}
