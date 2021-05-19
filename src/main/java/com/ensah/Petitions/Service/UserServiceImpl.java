package com.ensah.Petitions.Service;

import com.ensah.Petitions.DTO.UserDTO;
import com.ensah.Petitions.Entity.User;
import com.ensah.Petitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * User Service Implementation
 * UserServiceImpl also implement the UserDetailsService interface in addition to the UserService Interface.
 *
 */
@Service(value="userService")
public class UserServiceImpl  implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository ;


    /**
     * retrieve a given user by its username (email)
     * @return user
     */
    @Override
    public User findOne(String email) {
        User  user =  userRepository.findByEmail(email);
        return user;
    }


    /**
     * loadUserByUsername looks up the user through the DAO repository for the user object and returns a new
     * org.springframework.security.core.userdetails.User constructed with username, password, and a Set of granted authorities.
     * @param email the email of the user to fetch
     * @return org.springframework.security.core.userdetails.User constructed with username, password, and a Set of granted authorities.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findOne(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail() , user.getPassword(),new ArrayList<>());
    }
}
