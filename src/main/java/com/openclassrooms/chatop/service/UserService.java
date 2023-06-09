package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.UserDTO;
import com.openclassrooms.chatop.model.UserInfo;
import com.openclassrooms.chatop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void userRegister(UserDTO userDTO){
        UserInfo userInfo = convertDTOtoEntity(userDTO);
        userInfoRepository.save(userInfo);
    }

    private UserInfo convertDTOtoEntity(UserDTO user){
        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(user.getEmail());
        userInfo.setName(user.getName());
        userInfo.setPassword(encoder.encode(user.getPassword()));
        userInfo.setCreatedAt(user.getCreatedAt());
        userInfo.setUpdatedAt(user.getUpdatedAt());
        return userInfo;
    }

    private UserDTO convertEntitytoDTO(UserInfo user){
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(encoder.encode(user.getPassword()));
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
