package com.example.telexpress.service;

import com.example.telexpress.entity.ChatMessage;
import com.example.telexpress.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMesageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage sendMessage(Integer sourceuser, Integer destinationuser, String messageContent) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSourceuser(sourceuser);
        chatMessage.setDestinationuser(destinationuser);
        chatMessage.setMessage(messageContent);
        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage getLastMessageByDestination(Integer sourceuser) {
        return chatMessageRepository.findLastMessageByDestination(sourceuser);
    }

    public List<ChatMessage> getChatHistory(Long userId, Long agentId) {
        return chatMessageRepository.findChatHistoryByUserIdAndAgentId(userId, agentId);
    }
}
