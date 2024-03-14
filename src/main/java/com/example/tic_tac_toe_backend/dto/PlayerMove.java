package com.example.tic_tac_toe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerMove {

    private static String dtype = "PlayerMove";
    private String roomName;
    private int x;
    private int y;
    private String playerName;

}
