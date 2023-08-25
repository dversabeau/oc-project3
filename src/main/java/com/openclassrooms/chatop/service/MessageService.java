package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.MessageDTO;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<MessageDTO> getAll() {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            messageDTOs.add(convertEntityToDTO(message));
        }
        return messageDTOs;
    }

    public MessageDTO getOne(Long id) {
        Message message = messageRepository.getOne(id);
        MessageDTO messageDTO = convertEntityToDTO(message);
        return messageDTO;
    }

    public MessageDTO create(MessageDTO messageDTO) {
        Message message = convertDTOToEntity(messageDTO);
        Message message1 = messageRepository.save(message);
        MessageDTO dto = convertEntityToDTO(message1);
        return dto;
    }

    private MessageDTO convertEntityToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setId(message.getId());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setRental_id(message.getRentalId());
        messageDTO.setUser_id(message.getUserId());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setUpdatedAt(message.getUpdatedAt());
        return messageDTO;
    }

    private Message convertDTOToEntity(MessageDTO messageDTO) {
        Message message = new Message();

        message.setId(messageDTO.getId());
        message.setMessage(messageDTO.getMessage());
        message.setRentalId(messageDTO.getRental_id());
        message.setUserId(messageDTO.getUser_id());
        message.setCreatedAt(messageDTO.getCreatedAt());
        message.setUpdatedAt(messageDTO.getUpdatedAt());
        return message;
    }
}
