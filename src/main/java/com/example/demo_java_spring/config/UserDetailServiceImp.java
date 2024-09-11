package com.example.demo_java_spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo_java_spring.models.User;
import com.example.demo_java_spring.repository.UserRepository;

@Component
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserDetailServiceImp.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering loadUserByUsername user found");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found" + username);
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("User Authenticated Successfully");
        return new CustomUserDetail(user);
    }
}
