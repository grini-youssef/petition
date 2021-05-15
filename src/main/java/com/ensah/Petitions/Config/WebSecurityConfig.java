package com.ensah.Petitions.Config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Java Configuration class for Spring Security
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    /**
     * <b><u>Authentication:</u></b>
     * the AuthenticationManager Interface has exactly one method authenticate which is called to verify if the username and password provided by a user are truthy.
     * But the AuthenticationManager needs to know where the user’s username and password have been stored.
     * That is why we override the configure method where Spring will pass an AuthenticationManagerBuilder.
     * @param auth : The AuthenticationManagerBuilder accepts a custom implementation of the UserDetailsService interface.
     * Also at this stage, we declare that we will be using BCrypt to encode our passwords.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    /**
     * <b><u>Authorization</u></b>
     * Here we are configuring such that we will require authentication for all requests,
     * with the exception of /users/register & /users/login (We require those two endpoints to be available to all users to sign-up or login).
     * For the graceful handling of Unauthorized requests, we pass along a class that implements AuthenticationEntryPoint. We will return a 401 Unauthorized when we encounter an exception.
     * The JWT Token needs to be parsed to fetch roles that the SpringSecurityContext needs to become aware of before it goes on to check if the API’s permissions will allow it. So we pass along the JwtAuthenticationFilter
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/cases/Add","/cases/Delete","/cases/Edit","/cases/*/Signatures/*").authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Configuring the encoder used to encode password and adding it to the Spring Application Context as a bean.
     * Here we are using BCryptPasswordEncoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * The AuthenticationManager we just configured is added to the Spring Application Context
     * and is added as a bean by overriding the authecationManagerBean method.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * The JWT Token needs to be parsed to fetch roles that the SpringSecurityContext needs to become aware of before it goes on to check if the API’s permissions will allow it.
     * So we add the JwtAuthenticationFilter to the Spring Application Context as a bean in order to use it inside <b>Authorization</b> config method below.
     * @return JwtAuthenticationFilter
     * @throws Exception
     */
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

}
