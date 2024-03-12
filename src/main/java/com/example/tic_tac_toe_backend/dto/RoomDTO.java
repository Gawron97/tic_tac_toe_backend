package com.example.tic_tac_toe_backend.dto;


import com.example.tic_tac_toe_backend.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomDTO {

    private String roomName;
    private int freeSlots;
    private String player1;
    private String player2;

    public static RoomDTO of(Room room) {
        return RoomDTO.builder()
                .roomName(room.getRoomName())
                .freeSlots(room.getFreeSlots())
                .player1(room.getPlayer1())
                .player2(room.getPlayer2())
                .build();
    }

}
