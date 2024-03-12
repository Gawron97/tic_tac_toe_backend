package com.example.tic_tac_toe_backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {

    private List<Room> rooms;

    public Board() {
        rooms = new ArrayList<>(List.of(new Room("room1")));
    }

}
