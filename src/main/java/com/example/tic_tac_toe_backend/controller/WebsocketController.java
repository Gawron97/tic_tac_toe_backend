package com.example.tic_tac_toe_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebsocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("") // If we provide null path, it will use only prefix defines in WebSocketConfig -> config.setApplicationDestinationPrefixes("/app");
    public void forwardMessage(String message) {
        // Need to get user from Principal
        simpMessagingTemplate.convertAndSendToUser("user", "/topic", message);
        // Or I think we can do it this way
        simpMessagingTemplate.convertAndSend("/topic/user", message);
    }

}
