package com.example.demo72.messageTests;

import com.example.demo72.message.Message;
import com.example.demo72.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void testHome() throws Exception {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user1", "Message 1"));
        messages.add(new Message("user2", "Message 2"));

        when(messageRepository.findTop10ByOrderByIdDesc()).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders.get("/message-home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.model().attribute("messages", messages));
    }

    @Test
    @WithUserDetails("admin")
    public void testAddMessage_AdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/add-message")
                        .param("messageText", "New message"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/message-home"));

        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void testDeleteMessage_AdminRole() throws Exception {
        Long messageId = 1L;
        Optional<Message> optionalMessage = Optional.of(new Message("user1", "Message"));

        when(messageRepository.findById(messageId)).thenReturn(optionalMessage);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete-message")
                        .param("messageId", String.valueOf(messageId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/message-home"));

        verify(messageRepository, times(1)).delete(optionalMessage.get());
    }
}