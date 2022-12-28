package com.example.trains.api.timetableFile;

import com.example.trains.api.dto.TrainDTO;
import com.example.trains.api.entities.TrainEntity;
import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Plate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatesAndInOut {
    @JsonProperty("inOut")
    ArrayList<Cell> inOut;
    @JsonProperty("plates")
    ArrayList<Plate> plates;
    @JsonProperty("trains")
    List<TrainDTO> trains;
}
