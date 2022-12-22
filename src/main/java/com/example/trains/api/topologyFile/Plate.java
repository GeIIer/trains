package com.example.trains.api.topologyFile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id", scope = Cell.class)
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
                var line = lines1.get(i);
                lines.add(new PlateLine(line.get("x").asInt(), line.get("y").asInt(), line.get("number").asInt()));
            }
        }
        if (jsonNode.has("number")) this.number = jsonNode.get("number").asInt();
    }

    @Override
    public Object getInfo() {
        return this;
    }
}
