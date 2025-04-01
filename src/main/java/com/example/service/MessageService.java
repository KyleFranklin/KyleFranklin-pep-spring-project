package com.example.service;
import com.example.exception.InvalidMessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;


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
        //Checks if its a valid message
        if(message.getMessageText().length()>255 || message.getMessageText() == null || message.getMessageText().trim().isEmpty()){
            throw new InvalidMessageException("Message","Invalid Message");
        }

        //Checks if its a valid Id
        if(message.getPostedBy() == null || !(accountRepository.existsById(message.getPostedBy()))){
            throw new InvalidMessageException("Message","Id does not exist");
        }

        messageRepository.save(message);

        return message;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(Integer message_id){
        Optional<Message> message = messageRepository.findById(message_id);
        //Check if the message exists
        if(message.isPresent()){
            return message.get();
        }
        return null;
    }

    public Integer deleteMessageById(Integer message_id){
        //Check if the message exists
        if(!messageRepository.existsById(message_id)){
            return 0;
        }

        messageRepository.deleteById(message_id);

        return 1;
    }

    public Integer updateMessageById(Integer message_id, String updatedMessage){
        //checks if the updated message is valid
        if(updatedMessage.length()>255 || updatedMessage == null || updatedMessage.isEmpty()){
            throw new InvalidMessageException("Message","Invalid Message");
        }
        //checks if there is a message to update
        if(!messageRepository.existsById(message_id)){
            throw new InvalidMessageException("Message","Incorrect message Id");
        }
        //create a updated message
        Message message = messageRepository.getById(message_id);

        //update the message text
        message.setMessageText(updatedMessage);

        //update the repository
        messageRepository.save(message);

        return 1;
    }

    public List<Message> getAllMessagesById(Integer account_id){
        return messageRepository.findByPostedBy(account_id);
    }


}
