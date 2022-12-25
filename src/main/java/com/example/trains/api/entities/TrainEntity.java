package com.example.trains.api.entities;

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
    private Long idTrain;

    private String nameTrain;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private TypeTrainsEntity typeTrain;

    private int numberOfWagons;
}
