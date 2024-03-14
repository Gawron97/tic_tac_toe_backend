package com.example.tic_tac_toe_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StartGameMessage {

    private static String dtype = "StartGameMessage";
    private Boolean isStarting;

}
