package com.example.trains.api.dto;

import com.example.trains.api.entities.TypeTrainsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO implements Serializable {
    @JsonProperty("idTrain")
    private Long idTrain;

    @JsonProperty("nameTrain")
    private String nameTrain;

    @JsonProperty("typeTrain")
    private String typeTrain;

    @JsonProperty("numberOfWagons")
    private int numberOfWagons;

}
