package com.example.telexpress.repository;

import com.example.telexpress.entity.ChatMessage;

public interface ChatMessageRepositoryCustom {
    ChatMessage findLastMessageByDestination(Integer destinationuser);
}
