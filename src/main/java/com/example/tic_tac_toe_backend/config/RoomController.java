package com.example.tic_tac_toe_backend.config;

import com.example.tic_tac_toe_backend.dto.RoomDTO;
import com.example.tic_tac_toe_backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService boardService;

    @PostMapping("/chooseRoomForPlayer")
    public ResponseEntity<RoomDTO> chooseRoomForPlayer(@RequestParam String playerName) {
        return ResponseEntity.ok(boardService.chooseRoomForPlayer(playerName));
    }

}
