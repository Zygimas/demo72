package com.example.demo72.message;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String text;
    public Message() {
    }
    private Long userId;
    public Message(String author, String text) {
        this.author = author;
        this.text = text;
    }
    public Long getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}