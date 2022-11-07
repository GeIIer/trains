package com.example.trains.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@NoArgsConstructor
public class State implements Serializable {
    private ArrayList<String> state;
}
