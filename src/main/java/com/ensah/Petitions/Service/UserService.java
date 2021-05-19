package com.ensah.Petitions.Service;

import com.ensah.Petitions.Entity.User;

/**
 * User Service Interface
 * user is created only for admins
 *
 */
public interface UserService {

    User findOne(String username);

}
