package com.example.demo72.controllers;

import com.example.demo72.message.Message;
import com.example.demo72.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    private static final int MAX_MESSAGE_COUNT = 10;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @GetMapping("/message-home")
    public String home(Model model) {
        List<Message> messages = messageRepository.findTop10ByOrderByIdDesc();
        model.addAttribute("messages", messages);
        return "home";
    }
    @PostMapping("/add-message")
    public String addMessage(@RequestParam("messageText") String messageText,
                             Authentication authentication) {
        String username = authentication.getName();
        Message message = new Message(username, messageText);
        messageRepository.save(message);

        long messageCount = messageRepository.count();
        if (messageCount > MAX_MESSAGE_COUNT) {
            Optional<Message> oldestMessage = messageRepository.findOldestMessage();
            oldestMessage.ifPresent(messageRepository::delete);
        }
        return "redirect:/message-home";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete-message")
    public String deleteMessage(@RequestParam("messageId") Long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            messageRepository.delete(message);
        }
        return "redirect:/message-home";
    }
}