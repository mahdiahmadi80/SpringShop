package org.example.springshop.service.securityservice;

import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.User;
import org.example.springshop.model.UserPrincipal;
import org.example.springshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new UserNotFoundException("user not found"));
        return new UserPrincipal(user);
    }
}
