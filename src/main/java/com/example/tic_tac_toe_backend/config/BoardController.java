package com.example.tic_tac_toe_backend.config;

import com.example.tic_tac_toe_backend.dto.RoomDTO;
import com.example.tic_tac_toe_backend.entity.Room;
import com.example.tic_tac_toe_backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/chooseRoomForPlayer")
    public ResponseEntity<RoomDTO> chooseRoomForPlayer(@RequestParam String playerName) {
        return ResponseEntity.ok(boardService.chooseRoomForPlayer(playerName));
    }

}
