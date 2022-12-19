package com.example.trains.api.dto;

import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.Cell;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TopologyAndRecordsDTO {
    @JsonProperty("body")
    private ArrayList<ArrayList<Cell>> body;
    @JsonProperty("records")
    private ArrayList<Record> records;
}
