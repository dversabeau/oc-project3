package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message getOne(Long id);
    Message save(Message message);
    List<Message> findAll();

}
