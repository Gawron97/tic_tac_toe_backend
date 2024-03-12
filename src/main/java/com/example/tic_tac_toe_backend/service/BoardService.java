package com.example.tic_tac_toe_backend.service;

import com.example.tic_tac_toe_backend.dto.RoomDTO;
import com.example.tic_tac_toe_backend.entity.Board;
import com.example.tic_tac_toe_backend.entity.Room;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    private final Board board;

    public BoardService() {
        this.board = new Board();
    }

    public RoomDTO chooseRoomForPlayer(String playerName) {

        Room room;
        Optional<Room> optionalRoom = getRoomWithOneFreeSlot();
        if(optionalRoom.isPresent()) {
            room = optionalRoom.get();
            room.setPlayer2(playerName);
            room.setFreeSlots(0);
            return RoomDTO.of(room);
        }

        Optional<Room> optionalRoom2 = getRoomWithTwoFreeSlots();
        if(optionalRoom2.isPresent()) {
            room = optionalRoom2.get();
            room.setPlayer1(playerName);
            room.setFreeSlots(1);
            return RoomDTO.of(room);
        }

        Room newRoom = new Room("room" + (board.getRooms().size() + 1));
        newRoom.setPlayer1(playerName);
        newRoom.setFreeSlots(1);
        board.getRooms().add(newRoom);
        return RoomDTO.of(newRoom);

    }

    private Optional<Room> getRoomWithOneFreeSlot() {
        return board.getRooms().stream()
                .filter(room -> room.getFreeSlots() == 1)
                .findFirst();
    }

    private Optional<Room> getRoomWithTwoFreeSlots() {
        return board.getRooms().stream()
                .filter(room -> room.getFreeSlots() == 2)
                .findFirst();
    }

}
