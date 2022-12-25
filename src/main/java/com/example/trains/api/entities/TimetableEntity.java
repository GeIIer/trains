package com.example.trains.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "timetable")
public class TimetableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTimetable;

    private LocalDate timetableDate;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private TopologyEntity topology;

    private String fileName;

    private boolean status;
}
