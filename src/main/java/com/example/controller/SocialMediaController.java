package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.exception.InvalidAccountException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.Optional;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //Creating account logic
    @PostMapping("/register")
    public ResponseEntity createAccount(@RequestBody Account account){
        try { 
            Account accountcreated = accountService.createAccount(account);
            return ResponseEntity.status(200).body(accountcreated);

        } catch (InvalidAccountException e) {
            //specifically thorw error 409 if the username is a duplicate
            if(e.getErrorType().equals("username")){
                return ResponseEntity.status(409).body(e.getMessage());
            }
            else{
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){
        //create an account to check if the account has a valid login
        Account loggedIn = accountService.validLogin(account);

        //if the account is null, it means either the password is incorrect or the username was not found
        if(loggedIn != null){
            return ResponseEntity.status(200).body(loggedIn);
        }
        else{
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity createMessge(@RequestBody Message message){
        try {
            Message createdMessage = messageService.createMessage(message);
            //returns the created message with  the message in the body
            return ResponseEntity.status(200).body(createdMessage);
            
        } catch (InvalidMessageException e) {
            // if there is an invalid message due to constraints throw an error
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageByMessageId(@PathVariable Integer messageId){
        Optional<Message> message = messageService.getMessageByMessageId(messageId);

        //if the message is empty return a blank response
        if(!message.isPresent()){
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable Integer messageId){

        int rowsUpdated = messageService.deleteMessageById(messageId);

        //if the message was not found empty body
        if(rowsUpdated == 0){
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(rowsUpdated);
    }

}
