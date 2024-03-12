package com.learn.spring.withdurgesh.demo.blog.security;

import com.learn.spring.withdurgesh.demo.entities.User;
import com.learn.spring.withdurgesh.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by user
        User user = this.userRepo.findByEmail(username).get();
        return user;
    }
}
