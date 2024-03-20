package com.example.tic_tac_toe_backend.controller;

import com.example.tic_tac_toe_backend.dto.PlayerMove;
import com.example.tic_tac_toe_backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebsocketController {

    private final GameService gameService;

    @MessageMapping("/move") // If we provide null path, it will use only prefix defines in WebSocketConfig -> config.setApplicationDestinationPrefixes("/app");
    public void forwardMessage(PlayerMove playerMove) {
        gameService.makeMove(playerMove);
    }

}
