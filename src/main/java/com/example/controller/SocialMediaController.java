package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    //Creating account logic
    @PostMapping("/register")
    public ResponseEntity createAccount(@RequestBody Account account){
        try {
            // 0  = account created, 1 = duplicate user, 2 = password is not long enough
            Integer accountcreated = accountService.createAccount(account);

            if(accountcreated==0){
                return ResponseEntity.status(200).body(account);
            }

            else if(accountcreated == 1 ){
                return ResponseEntity.status(409).build();
            }

            else{
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
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

}
