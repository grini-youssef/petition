package com.ensah.Petitions.Service;

import com.ensah.Petitions.Entity.User;

/**
 * User Service Interface
 * user is either a visitor or a sector.
 *
 */
public interface UserService {

    User findOne(String username);

}
