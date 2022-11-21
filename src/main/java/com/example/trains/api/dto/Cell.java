package com.example.trains.api.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Cell implements Serializable {
    @NonNull
    private int id;
    @NonNull
    private int x;
    @NonNull
    private int y;

    private String type;

    private State state;

    public Cell (int id, int x, int y, State state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }

}
