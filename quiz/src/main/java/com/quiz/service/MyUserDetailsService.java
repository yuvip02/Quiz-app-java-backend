package com.quiz.service;


import com.quiz.model.UserPrincipal;
import com.quiz.model.Users;

import com.quiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Looking for user: " + username);

        Optional<Users> optionalUser = userRepo.findByUsernameIgnoreCase(username);

        if (optionalUser.isEmpty()) {
            System.out.println("User Not Found in DB: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Users user = optionalUser.get();
        System.out.println("Found User in DB: " + user);

        return new UserPrincipal(user);
    }


}