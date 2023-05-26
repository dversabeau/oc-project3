package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.chatop.service.MessageService;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/api/messages/{id}")
    public MessageDTO getOne(@PathVariable Long id){
        return messageService.getOne(id);
    }
    @PostMapping("/api/messages")
    public void create(@RequestBody MessageDTO messageDTO){
        messageService.create(messageDTO);
    }
}
