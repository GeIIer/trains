package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plate extends State implements Serializable {
    @NonNull
    @JsonProperty("dir")
    private boolean dir;
    @NonNull
    @JsonProperty("line1")
    private PlateLine line1;

    @JsonProperty("line2")
    private PlateLine line2;

    @JsonProperty("number")
    private int number;

    @Override
    public void setInfo(JsonNode jsonNode) {
        this.dir = jsonNode.get("dir").asBoolean();
        this.line1 = new PlateLine(jsonNode.get("line1"));
        this.line2 = new PlateLine(jsonNode.get("line2"));
        this.number = jsonNode.get("number").asInt();
    }

    @Override
    public void getInfo() {

    }
}
