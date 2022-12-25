package com.example.trains.api.factory;

import com.example.trains.api.dto.TimetableDTO;
import com.example.trains.api.entities.TimetableEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TimetableDTOFactory {
    public TimetableDTO makeTimetableDTO (TimetableEntity entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return  TimetableDTO.builder()
                .idTimetable(entity.getIdTimetable())
                .status(entity.isStatus())
                .timetableDate(String.valueOf(entity.getTimetableDate().format(formatter)))
                .build();
    }
}
