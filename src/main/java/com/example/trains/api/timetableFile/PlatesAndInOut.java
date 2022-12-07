package com.example.trains.api.timetableFile;

import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Plate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.ArrayList;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonSerialize(using = PlatesAndInOutSerializer.class)
public class PlatesAndInOut {
    @JsonProperty("inOut")
    ArrayList<Cell> inOut;
    @JsonProperty("plates")
    ArrayList<Plate> plates;
}
