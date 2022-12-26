package com.example.trains.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "train")
public class TrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("idTrain")
    private Long idTrain;

    @JsonProperty("nameTrain")
    private String nameTrain;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JsonProperty("typeTrain")
    private TypeTrainsEntity typeTrain;

    @JsonProperty("numberOfWagons")
    private int numberOfWagons;
}
