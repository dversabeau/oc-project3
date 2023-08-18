package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.MessageDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.chatop.service.MessageService;

import java.util.List;

@RestController
@SecurityRequirement(name = "javainuseapi")
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/messages")
    public List<MessageDTO> getAll(){
        return messageService.getAll();
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/messages/{id}")
    public MessageDTO getOne(@PathVariable Long id){
        return messageService.getOne(id);
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/messages")
    public void create(@RequestBody MessageDTO messageDTO){
        messageService.create(messageDTO);
    }
}
