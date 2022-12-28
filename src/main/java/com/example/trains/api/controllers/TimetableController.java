package com.example.trains.api.controllers;

import com.example.trains.api.dto.RecordAndWayDTO;
import com.example.trains.api.dto.TimetableDTO;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.factory.TimetableDTOFactory;
import com.example.trains.api.repositories.TimetableRepository;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import com.example.trains.api.service.FindWayService;
import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.TopologyFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private final FindWayService findWayService;
    @Autowired
    private final FileService fileService;

    private static final String GET_RECORDS = "";

    private static final String GET_RECORDS_AND_WAY = "/way";

    private static final String GET_ALL_TIMETABLE = "/all";

    private static final String SAVE_TIMETABLE = "/save";

    private static final String DELETE_TIMETABLE = "/{idTimetable}";

    @GetMapping(GET_ALL_TIMETABLE)
    public List<TimetableDTO> getAllTimetable(@RequestParam("idTopology") Long idTopology) {
        Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
        if (optionalTopologyEntity.isPresent()) {
            List<TimetableEntity> timetable = timetableRepository.findAllByTopology(optionalTopologyEntity.get());
            if (timetable != null) {
                return timetable.stream().map(timetableDTOFactory::makeTimetableDTO)
                        .collect(Collectors.toList());
            }
        }
        else
        {
            throw new RuntimeException("Ошибка: Нет такой топологии");
        }
        throw new RuntimeException("Ошибка: ");
    }

    @GetMapping(GET_RECORDS)
    public ArrayList<Record> getRecordsByTimetable (@RequestParam("idTopology") Long idTopology,
                                                    @RequestParam("date") String dateTimeString) {
        return getRecords(idTopology, dateTimeString);
    }

    @GetMapping(GET_RECORDS_AND_WAY) //TODO Оптимизировать говнокод
    public ArrayList<RecordAndWayDTO> getRecordsAndWays (@RequestParam("idTopology") Long idTopology,
                                                         @RequestParam("date") String dateTimeString) {
        try {
            ArrayList<Record> records = getRecords(idTopology, dateTimeString);
            Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
            TopologyEntity topology = optionalTopologyEntity.get();
            TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());
            return findWayService.getRecordsAndWays(records, topologyFileDTO);
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
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
            Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByTimetableDateAndTopology(date, topologyEntity);
            TimetableEntity timetableEntity;
            if (optionalTimetableEntity.isPresent()) {
                timetableEntity = optionalTimetableEntity.get();
            } else {
                timetableEntity = new TimetableEntity();
                timetableEntity.setTimetableDate(LocalDate.parse(dateTimeString, formatter));
                timetableEntity.setTopology(topologyEntity);
                timetableEntity.setFileName(topologyEntity.getFilename() + "/" + dateTimeString + ".bin");
                timetableEntity.setStatus(true);
            }
            TopologyFileDTO topologyFileDTO = fileService.loadTopology(topologyEntity.getFilename());
            try {
                ArrayList<RecordAndWayDTO> recordAndWayDTOS = findWayService.getRecordsAndWays(records, topologyFileDTO);
                fileService.saveTimetable(timetableEntity, recordAndWayDTOS);
                timetableRepository.save(timetableEntity);
            }
            catch (Exception ex){
                System.err.println("Ошибка: " + ex);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Невозможно проложить путь по заданному маршруту", ex);
            }

        }
        else {
            System.err.println("Топологии не существует");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Топологии не существует");
        }
    }
    private ArrayList<Record> getRecords(Long idTopology, String dateTimeString) {
        Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
        if (optionalTopologyEntity.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateTimeString, formatter);
            Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByTimetableDateAndTopology(date, optionalTopologyEntity.get());
            if (optionalTimetableEntity.isPresent()) {
                TimetableEntity timetable = optionalTimetableEntity.get();
                ArrayList<RecordAndWayDTO> recordAndWayDTOS = fileService.loadRecords(timetable.getFileName());
                ArrayList<Record> record = new ArrayList<Record>();
                for (int i = 0; i<recordAndWayDTOS.size(); i++) record.add(i, recordAndWayDTOS.get(i).getRecord());
                return record;
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

    @DeleteMapping(DELETE_TIMETABLE)
    public ResponseEntity<String> deleteTimetable (@PathVariable("idTimetable") Long idTimetable) {

        Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByIdTimetable(idTimetable);
        if (optionalTimetableEntity.isEmpty()) {
            return new ResponseEntity<>("Такого расписания нет", HttpStatus.OK);
        }
        TimetableEntity timetable = optionalTimetableEntity.get();
        if (fileService.deleteTimetable(timetable.getFileName()))
        {
            timetableRepository.deleteById(idTimetable);
            return new ResponseEntity<>("Удаление расписания произошло успешно", HttpStatus.OK);
        }
        return new ResponseEntity<>("Произошла ошибка при удалении", HttpStatus.OK);
    }

}
