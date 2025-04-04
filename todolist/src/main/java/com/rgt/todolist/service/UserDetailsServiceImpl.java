package com.rgt.todolist.service;

import com.rgt.todolist.model.User;
import com.rgt.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Autowired
    private UserRepository  userRepository;

    public UserDetails loadbyEmail(String Email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(Email);
        if (user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getFullName())
                    .password(user.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User not found with Email: " + Email);

    }
}
