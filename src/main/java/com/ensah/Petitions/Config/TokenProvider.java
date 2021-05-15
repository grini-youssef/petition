package com.ensah.Petitions.Config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <b>TokenProvider:</b> a component class that will handle all things JWT.
 * We will call the methods within this class to generate the JWT as well as validate the JWT when the user sends it back to us.
 *
 */
@Component
public class TokenProvider implements Serializable {

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = 2968540821894499163L;

    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;


    /**
     * Method to fetch username from JWT token.
     * @param token: JWT token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Method to fetch expiration date of the JWT token from the JWT Token
     * @param token: JWT Token
     * @return expiration date of the JWT Token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Methode to fetch a single claim from the JWT token
     * @param <T>
     * @param token: JWT Token
     * @param claimsResolver: function to fetch the wanted wlaim from the JWT token list of claims
     * @return the wanted claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method to fetch all the claims from the JWT token
     * @param token: JWT Token
     * @return claims of the JWT Token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Methode to test if the token is an Expired one.
     * @param token: JWT Token
     * @return boolen: true if token is expired, otherwise false.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * The generateToken method builds and signs the JWT Token that we will pass along to the user as soon as they authenticate.
     * Here is where as part of the payload, we will add the username, roles (comma separated), and the issuedAt and expiration timestamps.
     * @param authentication: the object created after successful authentication, it holds username authorities and other authentication info.
     * @return AuthToken: the JWT Authentication Token.
     */
    public String generateToken(Authentication authentication) {


        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    /**
     * validateToken basically checks if the username on the token payload matches the UserDetails. It also checks if the token has expired.
     * @param token: fetched from the header of the request.
     * @param userDetails: user info fetched from database.
     * @return boolean: true if the token is a valid token, otherwise false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Spring Security Context holds the information of an authenticated user represented as an Authentication object. In order to construct this Authentication object, we need to provide a UsernamePasswordAuthenticationToken which will later be used by our AuthenticationManager to Authenticate our user. To construct, we are passing along the user details as well as a collection of authorities(roles) that we parse from the JWT Token. This is exactly what's happening inside getAuthenticationToken.
     * @param token: JWT token from which we are fetching user roles
     * @param existingAuth
     * @param userDetails: user details
     * @return UsernamePasswordAuthenticationToken object which will later be used by our AuthenticationManager to Authenticate our user.
     */
    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        return new UsernamePasswordAuthenticationToken(userDetails, "");
    }

}