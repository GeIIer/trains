package com.example.trains.api.timetableFile;

import com.example.trains.api.entities.TypeTrainsEntity;
import com.example.trains.api.topologyFile.Plate;
import com.example.trains.api.topologyFile.PlateLine;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {

    private Plate plate;

    private PlateLine plateLine;

    private String trainName;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    private City departureCity;

    private City arrivalCity;

    private TypeTrainsEntity typeTrain;
}
