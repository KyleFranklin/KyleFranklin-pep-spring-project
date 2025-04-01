package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidAccountException;
import com.example.repository.AccountRepository;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //validate password
    public boolean validPasword(String password){
        //check if pasword is les then 4 characters
        if(password.length()<4){
            return false;
        }
        return true;
    }

    //create Account
    public Account createAccount(Account account){

        //check if the username is taken
        if(accountRepository.existsByUsername(account.getUsername())){
            throw new InvalidAccountException("username", "Username already exists.");
        }

        //validate password
        if(!validPasword(account.getPassword())){
            throw new InvalidAccountException("password", "Password must be atleast 4 characters.");
        }

        //update the database
        accountRepository.save(account);

        //Account created
        return account;
    }

    //Validate login
    public Account validLogin(Account given){
        Account account = accountRepository.findByUsername(given.getUsername());

        //account does not exist
        if(account == null){
            throw new InvalidAccountException("Login", "Account does not exist");
        }

        //account passwords do not match
        if(!account.getPassword().equals(given.getPassword())){
            throw new InvalidAccountException("Login", "Incorrect Password");
        }

        //valid login
        return account;
    }
}
