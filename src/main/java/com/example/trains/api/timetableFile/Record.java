package com.example.trains.api.timetableFile;

import com.example.trains.api.dto.TrainDTO;
import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.PlateLine;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {

    @JsonProperty("plate")
    private int plate;
    @JsonProperty("plateLine")
    private PlateLine plateLine;
    @JsonProperty("train")
    private TrainDTO train;
    @JsonProperty("arrivalTime")
    private LocalTime arrivalTime;
    @JsonProperty("departureTime")
    private LocalTime departureTime;
    @JsonProperty("departureCity")
    private String departureCity;
    @JsonProperty("arrivalCity")
    private String arrivalCity;
    @JsonProperty("in")
    private Cell in;
    @JsonProperty("out")
    private Cell out;
}
