package com.example.telexpress.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        for (WebSocketSession webSocketSession : sessions) {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
