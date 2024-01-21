package com.san.inv.sony_liv.service;

import java.util.HashSet;
import java.util.Set;

import com.san.inv.sony_liv.bo.ApplicationUser;
import com.san.inv.sony_liv.bo.Role;
import com.san.inv.sony_liv.repo.RoleRepository;
import com.san.inv.sony_liv.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationUser registerUser(String username, String password, String role){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role.toUpperCase()).get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

}
