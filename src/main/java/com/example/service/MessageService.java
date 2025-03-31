package com.example.service;
import com.example.exception.InvalidMessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        if(message.getMessageText().length()>255 || message.getMessageText() == null || message.getMessageText().trim().isEmpty()){
            throw new InvalidMessageException("Invalid Message");
        }

        if(message.getPostedBy()== null || !(accountRepository.existsById(message.getPostedBy()))){
            throw new InvalidMessageException("Invalid Message");
        }

        messageRepository.save(message);

        return message;
    }
}
