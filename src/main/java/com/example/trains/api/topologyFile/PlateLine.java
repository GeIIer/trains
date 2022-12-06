package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateLine implements Serializable {
    @NonNull
    @JsonProperty("x")
    private int x;
    @NonNull
    @JsonProperty("y")
    private int y;
    @JsonProperty("number")
    private int number;

    public PlateLine(JsonNode jsonNode) {
        if (jsonNode.has("x")) this.x = jsonNode.get("x").asInt();
        if (jsonNode.has("y")) this.y = jsonNode.get("y").asInt();
        if (jsonNode.has("number")) this.number = jsonNode.get("number").asInt();
    }
}
