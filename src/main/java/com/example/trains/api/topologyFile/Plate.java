package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plate extends State implements Serializable {
    @JsonProperty("dir")
    private boolean dir;
    @JsonProperty("lines")
    private ArrayList<PlateLine> lines = new ArrayList<>();
    @JsonProperty("number")
    private int number;

    @Override
    public void setInfo(JsonNode jsonNode) {
        if (jsonNode.has("dir")) this.dir = jsonNode.get("dir").asBoolean();
        if (jsonNode.has("lines")) {
            var lines1 = jsonNode.get("lines");
            for (int i = 0; i < lines1.size(); i++) {
                lines.add(new PlateLine(lines1.get("x").asInt(), lines1.get("y").asInt(), lines1.get("number").asInt()));
            }
        }
        if (jsonNode.has("number")) this.number = jsonNode.get("number").asInt();
    }

    @Override
    public Object getInfo() {
        return this;
    }

}
