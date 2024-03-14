package com.example.tic_tac_toe_backend.dto;


import com.example.tic_tac_toe_backend.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RoomDTO {

    private String dtype = "Room";
    private String roomName;
    private int freeSlots;
    private String player1;
    private String player2;
    private List<List<Integer>> fields;

    public static RoomDTO of(Room room) {
        return RoomDTO.builder()
                .dtype("Room")
                .roomName(room.getRoomName())
                .freeSlots(room.getFreeSlots())
                .player1(room.getPlayer1())
                .player2(room.getPlayer2())
                .fields(room.getFields())
                .build();
    }

}
