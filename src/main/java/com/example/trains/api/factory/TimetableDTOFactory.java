package com.example.trains.api.factory;

import com.example.trains.api.dto.TimetableDTO;
import com.example.trains.api.dto.TopologyDTO;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import org.springframework.stereotype.Component;

@Component
public class TimetableDTOFactory {
    public TimetableDTO makeTimetableDTO (TimetableEntity entity) {
        return  TimetableDTO.builder()
                .idTimetable(entity.getIdTimetable())
                .timetableDate(String.valueOf(entity.getTimetableDate()))
                .build();
    }
}
