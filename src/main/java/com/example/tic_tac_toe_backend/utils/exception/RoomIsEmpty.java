package com.example.tic_tac_toe_backend.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoomIsEmpty extends RuntimeException {

    private final HttpStatus status;

    public RoomIsEmpty() {
        super("Room is empty");
        status = HttpStatus.NOT_FOUND;
    }

    public RoomIsEmpty(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
