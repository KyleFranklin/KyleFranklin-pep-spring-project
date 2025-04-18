package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            //throw 400 for any othe reason
            else{
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }
    }

    //Login Logic
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){

        try {
            Account loggedIn = accountService.validLogin(account);
            return ResponseEntity.status(200).body(loggedIn);
            
        } catch (InvalidAccountException e) {
            // if there is an invalid message due to constraints throw an error
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    //Create Mesage Logic
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

    //Get All Messages Logic
    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }


    //Get Message By Mesasge Id logic
    @GetMapping("/messages/{message_id}")
    public ResponseEntity getMessageByMessageId(@PathVariable Integer message_id){
        Message message = messageService.getMessageByMessageId(message_id);

        //if the message is empty return a blank response
        if(message == null){
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(message);
    }

    //Deleteing message By message Id logic
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessageById(@PathVariable Integer message_id){

        int rowsUpdated = messageService.deleteMessageById(message_id);

        //if the message was not found empty body
        if(rowsUpdated == 0){
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(rowsUpdated);
    }

    //Updating messages By Id logic
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity updateMessageById(@PathVariable Integer message_id, @RequestBody Message newMessage){
        try {
            int rowsUpdated = messageService.updateMessageById(message_id, newMessage.getMessageText());
            return ResponseEntity.status(200).body(rowsUpdated);
            
        } catch (InvalidMessageException e) {
            // if there is an invalid message due to constraints throw an error
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    //Get All message by a specified accunt Id logic
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAllMessagesFromUserGivenId(@PathVariable Integer account_id){
        List<Message> messages = messageService.getAllMessagesById(account_id);
        return ResponseEntity.status(200).body(messages);
    }

}
