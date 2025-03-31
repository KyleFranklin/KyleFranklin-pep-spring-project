package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    //finds all messages by Account
    List<Message> findByPostedBy(Integer accountId);
}
