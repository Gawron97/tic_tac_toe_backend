package com.example.tic_tac_toe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpponentLeftMessage {

    private static String dtype = "OpponentLeftMessage";
    private String message = "Your opponent has left the game";

}
