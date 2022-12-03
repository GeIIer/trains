package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = TopologyFileDTODeserializer.class)
@JsonSerialize(using = TopologyFileDTOSerializer.class)
public class TopologyFileDTO implements Serializable {
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private ArrayList<ArrayList<Cell>> body;

    public Cell getCell(int x, int y) {
        int maxX = body.size();
        int maxY = body.get(0).size();
        if((maxX>x)&&(maxY>y))
        return body.get(x).get(y);
        else return null;
    }

    public ArrayList<Cell> getArray(int index) {
        int matrixLength = body.size();
        if (index < matrixLength && index >= 0) {
            return body.get(index);
        }
        throw new RuntimeException();
    }

    public ArrayList<ArrayList<Cell>> getBody() {
        return body;
    }
}
