package com.example.trains.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = TopologyFileDTODeserializer.class)
public class TopologyFileDTO implements Serializable {
    @NonNull
    @JsonProperty("title")
    private String title;

    @NonNull
    @JsonProperty("body")
    private ArrayList<ArrayList<Cell>> body;

    public Cell getCell(int x, int y) {
        int matrixLength = body.size();
        return body.get(x).get(y);
    }
}
