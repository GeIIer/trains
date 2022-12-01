package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable {
    @NonNull
    @JsonProperty("id")
    private int id;
    @NonNull
    @JsonProperty("x")
    private int x;
    @NonNull
    @JsonProperty("y")
    private int y;

    @JsonProperty("type")
    private String type;

    @JsonProperty("state")
    private State state;

    public Cell (int id, int x, int y, State state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }

}
