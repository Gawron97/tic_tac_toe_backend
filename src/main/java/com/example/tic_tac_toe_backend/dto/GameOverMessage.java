package com.example.tic_tac_toe_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameOverMessage {

    private static String dtype = "GameOverMessage";
    private boolean isWinner;
    private boolean isDraw;

}
