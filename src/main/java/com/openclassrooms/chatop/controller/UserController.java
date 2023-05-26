package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.UserDTO;
import com.openclassrooms.chatop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

//    @GetMapping("/api/auth/me")
//    public UserDTO getCurrentUser(@RequestBody Long id) {
//        return userService.getCurrentUser(id);
//    }

//    @PostMapping("/api/auth/login")
//    public void userAuthentication(@RequestBody UserDTO userDTO) {
//        userService.userAuthentication(userDTO);
//    }

    @PostMapping("/api/auth/register")
    public void userRegister(@RequestBody UserDTO userDTO) {
        userService.userRegister(userDTO);
    }


}
