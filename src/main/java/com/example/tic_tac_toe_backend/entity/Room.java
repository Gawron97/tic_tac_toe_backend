package com.example.tic_tac_toe_backend.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Room {

    private String roomName;
    private int freeSlots;
    private final int maxSlots = 2;
    private String player1;
    private String player2;
    private List<List<Integer>> fields;

    public Room(String roomName) {
        this.roomName = roomName;
        this.freeSlots = maxSlots;
    }

}
