package com.example.trains.api.timetableFile;

import com.example.trains.api.topologyFile.Plate;
import com.example.trains.api.topologyFile.PlateLine;
import com.example.trains.api.topologyFile.Step;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    private Plate plate;
    @JsonProperty("plateLine")
    private PlateLine plateLine;
    @JsonProperty("trainName")
    private String trainName;
    @JsonProperty("arrivalTime")
    private LocalTime arrivalTime;
    @JsonProperty("departureTime")
    private LocalTime departureTime;
    @JsonProperty("departureCity")
    private String departureCity;
    @JsonProperty("arrivalCity")
    private String arrivalCity;
    @JsonProperty("typeTrain")
    private String typeTrain;
    @JsonProperty("in")
    private Step in;
    @JsonProperty("out")
    private Step out;

}
