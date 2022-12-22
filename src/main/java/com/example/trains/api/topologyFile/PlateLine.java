package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlateLine implements Serializable {
    @JsonProperty("x")
    private int x;
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
