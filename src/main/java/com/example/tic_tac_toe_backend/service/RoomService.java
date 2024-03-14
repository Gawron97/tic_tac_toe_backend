package com.example.tic_tac_toe_backend.service;

import com.example.tic_tac_toe_backend.dto.RoomDTO;
import com.example.tic_tac_toe_backend.dto.StartGameMessage;
import com.example.tic_tac_toe_backend.entity.Room;
import com.example.tic_tac_toe_backend.repository.RoomRepository;
import com.example.tic_tac_toe_backend.utils.exception.RoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomDTO chooseRoomForPlayer(String playerName) {

        Room room;
        Optional<Room> optionalRoom = roomRepository.getRoomWithOneFreeSlot();
        if(optionalRoom.isPresent()) {
            room = optionalRoom.get();
            room.setPlayer2(playerName);
            room.setFreeSlots(0);
            sendStartGameMessage(room);
            return RoomDTO.of(room);
        }

        Optional<Room> optionalRoom2 = roomRepository.getRoomWithTwoFreeSlots();
        if(optionalRoom2.isPresent()) {
            room = optionalRoom2.get();
            room.setPlayer1(playerName);
            room.setFreeSlots(1);
            return RoomDTO.of(room);
        }

        Room newRoom = new Room("room" + roomRepository.getRoomCounter());
        roomRepository.setRoomCounter(roomRepository.getRoomCounter() + 1);
        newRoom.setPlayer1(playerName);
        newRoom.setFreeSlots(1);
        roomRepository.addRoom(newRoom);
        return RoomDTO.of(newRoom);

    }

    private void sendStartGameMessage(Room room) {
        boolean isStarting = new Random().nextBoolean();
        sendStartGameMessageToPlayer(room.getPlayer1(), isStarting);
        sendStartGameMessageToPlayer(room.getPlayer2(), !isStarting);
    }

    private void sendStartGameMessageToPlayer(String playerName, boolean isStarting) {
        log.info("Sending starting game info to /topic/" + playerName + " with message " + isStarting);
        simpMessagingTemplate.convertAndSend("/topic/" + playerName, new StartGameMessage(isStarting));
    }

    public void deletePlayerFromRoom(String roomName, String playerName) {

        Room room = roomRepository.getRoomByName(roomName);
        if(room.getFreeSlots() == 1) {
            roomRepository.removeRoom(room);
        } else {
            if(room.getPlayer1().equals(playerName)) {
                room.setPlayer1(room.getPlayer2());
            }
            room.setPlayer2(null);
            room.setFreeSlots(1);
            room.initializeFields();
            simpMessagingTemplate.convertAndSend("/topic/" + room.getPlayer1(), RoomDTO.of(room));
        }
    }

    public RoomDTO getRoom(String roomName) {
        return RoomDTO.of(roomRepository.getRoomByName(roomName));
    }
}
