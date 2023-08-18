package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.UserDTO;
import com.openclassrooms.chatop.model.ResponseUser;
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

    public ResponseUser getUser(String username) {
        UserInfo userInfo = userInfoRepository.findByName(username);
        ResponseUser responseUser = convertUsertoResponseUser(userInfo);
        return responseUser;
    }

    public UserInfo getCurrentUser(String username) {
        UserInfo userInfo = userInfoRepository.findByName(username);
        return userInfo;
    }

    private UserInfo convertDTOtoEntity(UserDTO user) {
        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(user.getEmail());
        userInfo.setName(user.getName());
        userInfo.setPassword(encoder.encode(user.getPassword()));
        userInfo.setCreatedAt(user.getCreatedAt());
        userInfo.setUpdatedAt(user.getUpdatedAt());
        return userInfo;
    }

    public UserDTO convertEntitytoDTO(UserInfo user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(encoder.encode(user.getPassword()));
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    private ResponseUser convertUsertoResponseUser(UserInfo userInfo) {
        ResponseUser responseUser = new ResponseUser();
        responseUser.setId(userInfo.getId());
        responseUser.setEmail(userInfo.getEmail());
        responseUser.setCreated_at(userInfo.getCreatedAt());
        responseUser.setUsername(userInfo.getName());
        responseUser.setUpdated_at(userInfo.getUpdatedAt());
        return responseUser;
    }
}
