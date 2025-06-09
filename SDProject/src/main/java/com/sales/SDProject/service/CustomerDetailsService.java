package com.sales.SDProject.service;


import com.sales.SDProject.dao.AdminRepository;
import com.sales.SDProject.model.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        admin adminUser = adminRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(adminUser.getUsername())
                .password(adminUser.getPassword()) // Hashed password stored in DB
                .authorities(Collections.emptyList())
                .build();
    }
}
