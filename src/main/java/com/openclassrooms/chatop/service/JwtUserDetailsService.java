package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.UserDTO;
import com.openclassrooms.chatop.model.UserInfo;
import com.openclassrooms.chatop.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder bcryptEncoder;

    public JwtUserDetailsService(UserInfoRepository userInfoRepository, PasswordEncoder bcryptEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserInfo save(UserDTO userDTO) {
        UserInfo newUser = new UserInfo();
        newUser.setName(userDTO.getName());
        newUser.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
        return userInfoRepository.save(newUser);
    }

}
