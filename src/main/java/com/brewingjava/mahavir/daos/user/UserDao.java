package com.brewingjava.mahavir.daos.user;

import com.brewingjava.mahavir.entities.user.UserRequest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserDao extends MongoRepository<UserRequest,String> {
    
    public UserRequest findByEmail(String email);
}
