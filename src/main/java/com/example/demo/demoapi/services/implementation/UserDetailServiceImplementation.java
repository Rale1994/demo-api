package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.entity.CustomUserDetails;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOp = userRepository.findByUsername(username);

        if (!userEntityOp.isPresent()) throw new UsernameNotFoundException(username);

        User user = userEntityOp.get();
        return new CustomUserDetails(user);
    }
}
