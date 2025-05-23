package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.model.UserPrincipal;
import org.example.springshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            System.out.println("user not find");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
