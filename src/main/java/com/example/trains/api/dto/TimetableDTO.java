package com.example.trains.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTimetable;

    private String timetableDate;
}
