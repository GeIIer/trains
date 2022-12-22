package com.example.trains.api.dto;

import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.Step;
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
public class RecordAndWayDTO {
    @JsonProperty("record")
    private Record record;
    @JsonProperty("way")
    private ArrayList<Step> way = new ArrayList<>();

}
