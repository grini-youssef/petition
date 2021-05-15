package com.ensah.Petitions.Repository;

import com.ensah.Petitions.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *  Spring Data MongoDB based User Repository
 */

public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String email);
}
