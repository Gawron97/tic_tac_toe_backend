package com.example.tic_tac_toe_backend.service;

import com.example.tic_tac_toe_backend.dto.RoomDTO;
import com.example.tic_tac_toe_backend.dto.StartGameMessage;
import com.example.tic_tac_toe_backend.entity.Room;
import com.example.tic_tac_toe_backend.utils.exception.RoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomService {

    private final List<Room> rooms;
    private int roomCounter = 1;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        rooms = new ArrayList<>(List.of(new Room("room" + roomCounter++)));
    }

    public RoomDTO chooseRoomForPlayer(String playerName) {

        Room room;
        Optional<Room> optionalRoom = getRoomWithOneFreeSlot();
        if(optionalRoom.isPresent()) {
            room = optionalRoom.get();
            room.setPlayer2(playerName);
            room.setFreeSlots(0);
            sendStartGameMessage(room);
            return RoomDTO.of(room);
        }

        Optional<Room> optionalRoom2 = getRoomWithTwoFreeSlots();
        if(optionalRoom2.isPresent()) {
            room = optionalRoom2.get();
            room.setPlayer1(playerName);
            room.setFreeSlots(1);
            return RoomDTO.of(room);
        }

        Room newRoom = new Room("room" + roomCounter++);
        newRoom.setPlayer1(playerName);
        newRoom.setFreeSlots(1);
        rooms.add(newRoom);
        return RoomDTO.of(newRoom);

    }

    private void sendStartGameMessage(Room room) {
        sendStartGameMessageToPlayer(room.getPlayer1());
        sendStartGameMessageToPlayer(room.getPlayer2());
    }

    private void sendStartGameMessageToPlayer(String playerName) {
        log.info("Sending starting game info to /topic/" + playerName);
        simpMessagingTemplate.convertAndSend("/topic/" + playerName, new StartGameMessage("Game starting"));
    }

    private Optional<Room> getRoomWithOneFreeSlot() {
        return rooms.stream()
                .filter(room -> room.getFreeSlots() == 1)
                .findFirst();
    }

    private Optional<Room> getRoomWithTwoFreeSlots() {
        return rooms.stream()
                .filter(room -> room.getFreeSlots() == 2)
                .findFirst();
    }

    public void deletePlayerFromRoom(String roomName, String playerName) {
        rooms.stream()
                .filter(room -> room.getRoomName().equals(roomName))
                .findFirst()
                .ifPresentOrElse(room -> {
                    if(room.getFreeSlots() == 1) {
                        rooms.remove(room);
                    } else {
                        if(room.getPlayer1().equals(playerName)) {
                            room.setPlayer1(room.getPlayer2());
                            room.setPlayer2(null);
                            room.setFreeSlots(1);
                        } else {
                            room.setPlayer2(null);
                            room.setFreeSlots(1);
                        }
                    }
                }, RoomNotFoundException::new);
    }

}
