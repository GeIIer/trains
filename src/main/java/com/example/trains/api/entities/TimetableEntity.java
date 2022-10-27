package com.example.trains.api.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timetable")
public class TimetableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTimetable;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private TopologyEntity topology;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private DirectionEntity direction;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private TrainEntity train;

    private int trackNumber;
    private String platformNumber;

    private Date departureTime;
    private Date arrivalTime;

}
