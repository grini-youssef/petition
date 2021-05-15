package com.ensah.Petitions.Rest;

import com.ensah.Petitions.Config.TokenProvider;
import com.ensah.Petitions.DTO.AuthResponse;
import com.ensah.Petitions.DTO.UserDTO;
import com.ensah.Petitions.Entity.User;
import com.ensah.Petitions.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Authentication Endpoint Controller
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UsersRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;


    /**
     * Endpoint to get authenticated
     * @param loginUser : login and password
     * @return AuthToken: it is returned to be used by the client in its future requests
     */
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserDTO loginUser) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User user = userService.findOne(loginUser.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user));
    }

}
