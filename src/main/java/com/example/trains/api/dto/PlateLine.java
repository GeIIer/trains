package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateLine {
    @NonNull
    @JsonProperty("x")
    private int x;
    @NonNull
    @JsonProperty("y")
    private int y;
    @JsonProperty("number")
    private int number;

    public PlateLine(JsonNode jsonNode) {
        this.x = jsonNode.get("x").asInt();
        this.y = jsonNode.get("y").asInt();
        this.number = jsonNode.get("number").asInt();
    }
}
