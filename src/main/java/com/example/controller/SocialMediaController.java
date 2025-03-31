package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

        //if the account is null, it mmeans either the password is incorrect or the username was not found
        if(loggedIn != null){
            return ResponseEntity.status(200).body(loggedIn);
        }
        else{
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity login(@RequestBody Message message){
        try {
            Message createdMessage = messageService.createMessage(message);

            return ResponseEntity.status(200).body(createdMessage);
        } catch (InvalidMessageException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
