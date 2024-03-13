package com.example.tic_tac_toe_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardDTO {

    private List<List<Integer>> fields;

}
