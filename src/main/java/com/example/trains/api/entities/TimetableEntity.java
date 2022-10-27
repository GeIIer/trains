package com.example.trains.api.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timetable")
public class TimetableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTimetable;
}
