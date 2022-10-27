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
@Table(name = "direction")
public class DirectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long idDirection;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private CityEntity departureCity;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private CityEntity arrivalCity;
}
