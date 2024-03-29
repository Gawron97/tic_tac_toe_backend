package com.example.tic_tac_toe_backend.service;

import com.example.tic_tac_toe_backend.dto.BoardDTO;
import com.example.tic_tac_toe_backend.dto.GameOverMessage;
import com.example.tic_tac_toe_backend.dto.PlayerMove;
import com.example.tic_tac_toe_backend.entity.Player;
import com.example.tic_tac_toe_backend.entity.Room;
import com.example.tic_tac_toe_backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public void makeMove(PlayerMove playerMove) {

        Room room = roomRepository.getRoomByName(playerMove.getRoomName());

        makeMoveOnBoard(room, playerMove);

        BoardDTO board = new BoardDTO(room.getFields());

        if(room.getPlayer1().getName().equals(playerMove.getPlayerName())) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), board);
        } else {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), board);
        }

        if(checkWin(room)) {
            startNewGame(room);
            return;
        }
        if(checkDraw(room)){
            startNewGame(room);
            return;
        }
    }

    private void makeMoveOnBoard(Room room, PlayerMove playerMove) {
        List<List<Integer>> fields = room.getFields();
        int symbol = 1;
        if(room.getPlayer2().getName().equals(playerMove.getPlayerName())) {
            symbol = 2;
        }

        fields.get(playerMove.getX()).set(playerMove.getY(), symbol);
        room.setFields(fields);

        Player player1 = room.getPlayer1();
        player1.setStarting(!player1.isStarting());

        Player player2 = room.getPlayer2();
        player2.setStarting(!player2.isStarting());
    }

    private boolean checkWin(Room room) {
        if(checkRows(room)) {
            return true;
        }
        if(checkColumns(room)) {
            return true;
        }
        if(checkDiagonal(room)) {
            return true;
        }
        return false;
    }

    private boolean checkRows(Room room) {
        List<List<Integer>> fields = room.getFields();
        for(int i = 0; i < 3; i++) {
            if(fields.get(i).get(0) == 1 && fields.get(i).get(1) == 1 && fields.get(i).get(2) == 1) {
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(true, false));
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(false, false));
                return true;
            }
            if(fields.get(i).get(0) == 2 && fields.get(i).get(1) == 2 && fields.get(i).get(2) == 2) {
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(false, false));
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(true, false));
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(Room room) {
        List<List<Integer>> fields = room.getFields();
        for(int i = 0; i < 3; i++) {
            if(fields.get(0).get(i) == 1 && fields.get(1).get(i) == 1 && fields.get(2).get(i) == 1) {
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(true, false));
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(false, false));
                return true;
            }
            if(fields.get(0).get(i) == 2 && fields.get(1).get(i) == 2 && fields.get(2).get(i) == 2) {
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(false, false));
                simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(true, false));
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(Room room) {
        List<List<Integer>> fields = room.getFields();
        if(fields.get(0).get(0) == 1 && fields.get(1).get(1) == 1 && fields.get(2).get(2) == 1) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(true, false));
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(false, false));
            return true;
        }
        if(fields.get(0).get(0) == 2 && fields.get(1).get(1) == 2 && fields.get(2).get(2) == 2) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(false, false));
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(true, false));
            return true;
        }
        if(fields.get(0).get(2) == 1 && fields.get(1).get(1) == 1 && fields.get(2).get(0) == 1) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(true, false));
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(false, false));
            return true;
        }
        if(fields.get(0).get(2) == 2 && fields.get(1).get(1) == 2 && fields.get(2).get(0) == 2) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(false, false));
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(true, false));
            return true;
        }
        return false;
    }

    private boolean checkDraw(Room room) {
        int countEmptyFields = 0;
        List<List<Integer>> fields = room.getFields();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(fields.get(i).get(j) == 0) {
                    countEmptyFields++;
                }
            }
        }
        if(countEmptyFields == 0) {
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1().getName(), new GameOverMessage(false, true));
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer2().getName(), new GameOverMessage(false, true));
            return true;
        }
        return false;
    }

    private void startNewGame(Room room) {
        room.initializeFields();
    }


}
