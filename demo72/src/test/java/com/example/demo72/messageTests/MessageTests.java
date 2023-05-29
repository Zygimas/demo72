package com.example.demo72.messageTests;

import com.example.demo72.message.Message;
import com.example.demo72.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MessageTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testSaveMessage() {
        Message message = new Message("John", "Hello!");

        Message savedMessage = messageRepository.save(message);

        assertNotNull(savedMessage.getId());

        assertEquals("John", savedMessage.getAuthor());
        assertEquals("Hello!", savedMessage.getText());
    }

    @Test
    public void testDeleteMessage() {
        Message message = new Message("John", "Hello!");

        Message savedMessage = messageRepository.save(message);

        assertNotNull(messageRepository.findById(savedMessage.getId()).orElse(null));

        messageRepository.delete(savedMessage);

        assertNull(messageRepository.findById(savedMessage.getId()).orElse(null));
    }
}
