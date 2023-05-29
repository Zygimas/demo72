package com.example.demo72.repository;

import com.example.demo72.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Message> findTop10ByOrderByIdDesc();

    @Query(value = "SELECT * FROM message ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<Message> findOldestMessage();

}