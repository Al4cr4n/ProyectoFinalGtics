package com.example.telexpress.controller;

import com.example.telexpress.entity.ChatMessage;
import com.example.telexpress.service.ChatMesageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/chat")
public class RestChatController {

    @Autowired
    private ChatMesageService chatMessageService;

    // Map para mantener colas de mensajes por usuario
    private final Map<Integer, BlockingQueue<ChatMessage>> userQueues = new ConcurrentHashMap<>();

    // Método para obtener o crear la cola de un usuario
    private BlockingQueue<ChatMessage> getQueueForUser(Integer userId) {
        return userQueues.computeIfAbsent(userId, k -> new LinkedBlockingQueue<>());
    }

    // Método para enviar un mensaje
    public void sendMessageToQueue(ChatMessage message) {
        // Obtener la cola del usuario destino y agregar el mensaje
        System.out.println("sourceuser: " + message.getSourceuser() + " destinationuser: " + message.getDestinationuser() + " message: " + message.getMessage());
        BlockingQueue<ChatMessage> queue = getQueueForUser(message.getDestinationuser());
        boolean offered = queue.offer(message);
        if (!offered) {
            System.err.println("Failed to add message to the queue for user: " + message.getDestinationuser());
        }
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestParam Integer sourceuser, @RequestParam Integer destinationuser, @RequestParam String message) {
        System.out.println("sourceuser: " + sourceuser + " destinationuser: " + destinationuser + " message: " + message);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSourceuser(sourceuser);
        chatMessage.setDestinationuser(destinationuser);
        chatMessage.setMessage(message);
        sendMessageToQueue(chatMessage);
        return chatMessageService.sendMessage(sourceuser, destinationuser, message);
    }

    @GetMapping("/lastMessage")
    public ChatMessage getLastMessageBySource(@RequestParam Integer destinationuser) {
        System.out.println("sourceuser: " + destinationuser);
        return chatMessageService.getLastMessageByDestination(destinationuser);
    }

    @GetMapping("/longPoll")
    public ResponseEntity<ChatMessage> longPoll(@RequestParam Integer destinationuser) {
        try {
            BlockingQueue<ChatMessage> userQueue = getQueueForUser(destinationuser);

            // Esperar por un mensaje nuevo
            ChatMessage message = userQueue.poll(30, TimeUnit.SECONDS);

            if (message != null) {
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.noContent().build();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(@RequestParam Long userId, @RequestParam Long agentId) {
        return chatMessageService.getChatHistory(userId, agentId);
    }
}
