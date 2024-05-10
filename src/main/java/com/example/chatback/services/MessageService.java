package com.example.chatback.services;

import com.example.chatback.entity.Message;
import com.example.chatback.repository.MessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class MessageService {
    private final MessageRepository messageRepository;
    public void save (Message message){
        messageRepository.save(message);
    }
}
