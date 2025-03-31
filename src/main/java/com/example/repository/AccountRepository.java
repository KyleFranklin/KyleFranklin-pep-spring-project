package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    // Check if an Account exists by username
    boolean existsByUsername (String username);

    //Find the account tied to a specified username
    Account findByUsername(String username);
}
