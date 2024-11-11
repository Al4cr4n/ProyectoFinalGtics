package com.example.telexpress.controller;

import com.example.telexpress.entity.ChatMessage;
import com.example.telexpress.service.ChatMesageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping("/api/chat")
public class RestChatController {

    @Autowired
    private ChatMesageService chatMessageService;

    private final BlockingQueue<ChatMessage> messageQueue = new LinkedBlockingQueue<>();

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestParam Integer sourceuser, @RequestParam Integer destinationuser, @RequestParam String message) {
        System.out.println("sourceuser: " + sourceuser + " destinationuser: " + destinationuser + " message: " + message);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSourceuser(sourceuser);
        chatMessage.setDestinationuser(destinationuser);
        chatMessage.setMessage(message);

        messageQueue.offer(chatMessage);
        return chatMessageService.sendMessage(sourceuser, destinationuser, message);
    }

    @GetMapping("/lastMessage")
    public ChatMessage getLastMessageBySource(@RequestParam Integer destinationuser) {
        System.out.println("sourceuser: " + destinationuser);
        return chatMessageService.getLastMessageByDestination(destinationuser);
    }

    @GetMapping("/longPoll")
    public ChatMessage longPoll(@RequestParam Integer destinationuser) throws InterruptedException {
        System.out.println("sourceuser: " + destinationuser);
        ChatMessage message;
        do {
            message = messageQueue.take();
        } while (!message.getDestinationuser().equals(destinationuser));
        return message;
    }
}
