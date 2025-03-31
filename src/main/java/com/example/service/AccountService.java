package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //validate password
    public boolean validPasword(String password){
        if(password.length()<3){
            return false;
        }
        return true;
    }

    //create Account
    public Integer createAccount(Account account){

        //check if the username is taken
        if(accountRepository.existsByUsername(account.getUsername())){
            return 1;
        }

        //validate password
        if(!validPasword(account.getPassword())){
            return 2;
        }

        //update the database
        accountRepository.save(account);

        //Account created
        return 0;
    }

    //Validate login
    public Account validLogin(Account given){
        System.out.println("Validating login for account: " + given);
        Account account = accountRepository.findByUsername(given.getUsername());

        //account does not exist
        if(account == null){
            System.out.println("Account not found: " + given);
            return null;
        }

        System.out.println("Account found: " + given);


        //account passwords do not match
        if(!account.getPassword().equals(given.getPassword())){
            System.out.println("Passwords do not match for account: " + given.getUsername());
            return null;
        }

        //valid login
        System.out.println("Valid login for username: " + given.getUsername());
        return account;
    }
}
