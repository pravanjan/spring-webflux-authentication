package com.pravanjan.spring_webflux_authentication.services;

import com.pravanjan.spring_webflux_authentication.model.User;
import com.pravanjan.spring_webflux_authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Mono<User> findByUsername (String userName){
        return userRepository.findByUserName(userName);
    }
    public Mono<User> save(User user){
        return userRepository.save(user);
    }
}
