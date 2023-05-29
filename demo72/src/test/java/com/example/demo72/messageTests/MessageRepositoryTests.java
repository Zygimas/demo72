package com.example.demo72.messageTests;

import com.example.demo72.message.Message;
import com.example.demo72.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MessageRepositoryTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testFindTop10ByOrderByIdDesc() {
        for (int i = 0; i < 10; i++) {
            Message message = new Message("Author " + i, "Text " + i);
            messageRepository.save(message);
        }

        List<Message> top10Messages = messageRepository.findTop10ByOrderByIdDesc();

        assertEquals(10, top10Messages.size());

        for (int i = 0; i < 10; i++) {
            Message message = top10Messages.get(i);
            assertEquals("Author " + (11 - i), message.getAuthor());
            assertEquals("Text " + (11 - i), message.getText());
        }
    }

    @Test
    public void testFindOldestMessage() {
        for (int i = 0; i < 5; i++) {
            Message message = new Message("Author " + i, "Text " + i);
            messageRepository.save(message);
        }

        Optional<Message> oldestMessage = messageRepository.findOldestMessage();

        assertTrue(oldestMessage.isPresent());

        Message message = oldestMessage.get();
        assertEquals("Author 0", message.getAuthor());
        assertEquals("Text 0", message.getText());
    }
}
