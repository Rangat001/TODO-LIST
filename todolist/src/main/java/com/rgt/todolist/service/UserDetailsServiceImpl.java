package com.rgt.todolist.service;

import com.rgt.todolist.model.User;
import com.rgt.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository  userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User not found with Email: " + email);
    }



//    public UserDetails loadbyEmail(String Email) throws UsernameNotFoundException{
//        User user = userRepository.findByEmail(Email);
//        if (user!=null){
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getFullName())
//                    .password(user.getPassword())
//                    .build();
//        }
//
//
//    }
}
