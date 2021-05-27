package com.org.obtech.oauth2.service;

import com.org.obtech.oauth2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//we user the Userdetailed service which is a Spring security
//class to retrieve user related data,
//it is also used by the authentication provider to load user details
//required during authentication
//All we need to do is override the loadbyusername method
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUserName(username);
        return Optional.ofNullable(user)
                .filter(a_user -> !a_user.isEmpty())
                .map(a_user -> org.springframework.security.core.userdetails.User
                        .withUsername(a_user.get().getAccount().getUsername())
                        .password(a_user.get().getAccount().getPassword())
                        .accountExpired(false)
                        .authorities(a_user.get().getAccount().getRoles())
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials, username or password is wrong"));
    }
}
