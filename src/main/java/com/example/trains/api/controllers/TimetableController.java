package com.example.trains.api.controllers;

import com.example.trains.api.dto.TimetableDTO;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.factory.TimetableDTOFactory;
import com.example.trains.api.repositories.TimetableRepository;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import com.example.trains.api.timetableFile.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/schedule"})
public class TimetableController {

    @Autowired
    private final TimetableRepository timetableRepository;

    @Autowired
    private final TopologyRepository topologyRepository;
    @Autowired
    private final TimetableDTOFactory timetableDTOFactory;

    @Autowired
    private final FileService fileService;

    private static final String GET_RECORDS = "";

    private static final String GET_ALL_TIMETABLE = "/all";

    private static final String SAVE_TIMETABLE = "/save";

    @GetMapping(GET_ALL_TIMETABLE)
    public List<TimetableDTO> getAllTimetable(@RequestParam("idTopology") Long idTopology) {
        List<TimetableEntity> timetable = timetableRepository.findAllByTopology(idTopology);
        if (timetable != null) {
            return timetable.stream().map(timetableDTOFactory::makeTimetableDTO)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("Ошибка: ");
    }

    @GetMapping(GET_RECORDS)
    public ArrayList<Record> getRecordsByTimetable (@RequestParam("idTopology") Long idTopology,
                                               @RequestParam("date") String dateTimeString) {
        Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
        if (optionalTopologyEntity.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateTimeString, formatter);
            Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByTimetableDateAndIdTopology(date, idTopology);
            if (optionalTimetableEntity.isPresent()) {
                TopologyEntity topology = optionalTopologyEntity.get();
                TimetableEntity timetable = optionalTimetableEntity.get();
                return fileService.loadRecords(topology.getFilename(), timetable.getFileName());
            }
            else {
                throw new RuntimeException("Нет записей в расписании: ");
            }
        }
        else
        {
            throw new RuntimeException("Топологии не существует: ");
        }
    }
    @PostMapping(SAVE_TIMETABLE)
    public void createTimetable (@RequestParam("idTopology") Long idTopology,
                                 @RequestParam("date") String dateTimeString,
                                 @RequestBody ArrayList<Record> records) {
        Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
        if (optionalTopologyEntity.isPresent())
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            TopologyEntity topologyEntity = optionalTopologyEntity.get();
            LocalDate date = LocalDate.parse(dateTimeString, formatter);
            Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByTimetableDateAndIdTopology(date, idTopology);
            TimetableEntity timetableEntity;
            if (optionalTimetableEntity.isPresent()) {
                timetableEntity = optionalTimetableEntity.get();
            } else {
                timetableEntity = new TimetableEntity();
                timetableEntity.setTimetableDate(LocalDate.parse(dateTimeString, formatter));
                timetableEntity.setTopology(topologyEntity);
                timetableEntity.setFileName(dateTimeString);
                timetableRepository.save(timetableEntity);
            }
            fileService.saveTimetable(topologyEntity, timetableEntity, records);
        }
        else {
            throw new RuntimeException("Топологии не существует: ");
        }
    }
}
