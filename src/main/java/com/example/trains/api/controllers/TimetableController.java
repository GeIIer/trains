package com.example.trains.api.controllers;

import com.example.trains.api.dto.TimetableDTO;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.factory.TimetableDTOFactory;
import com.example.trains.api.repositories.TimetableRepository;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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

    private static final String GET_ALL_TIMETABLE = "/all";

    private static final String SAVE_TIMETABLE = "/save";

    private static final String CREATE_TIMETABLE = "/create";

    @GetMapping(GET_ALL_TIMETABLE)
    public List<TimetableDTO> getAllTimetable(@RequestParam("idTopology") Long idTopology) {
        List<TimetableEntity> timetable = timetableRepository.findAllByTopology(idTopology);
        if (timetable != null) {
            return timetable.stream().map(timetableDTOFactory::makeTimetableDTO)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("Ошибка: ");
    }

    @PostMapping(SAVE_TIMETABLE)
    public void save (@RequestParam("idTimetable") Long idTimetable,
                      @RequestBody Record[] records) {
        TimetableEntity timetable = timetableRepository.findByIdTimetable(idTimetable);
        if (timetable != null){
            fileService.saveTimetable(records);
        }
        throw new RuntimeException("Расписания не существует: ");
    }
    @PostMapping(CREATE_TIMETABLE)
    public void createTimetable (@RequestParam("idTopology") Long idTopology,
                                 @RequestBody TimetableDTO timetable) {
        if (topologyRepository.existsByIdTopology(idTopology)){
            TimetableEntity timetableEntity = new TimetableEntity();
            timetableEntity.setIdTimetable(timetable.getIdTimetable());
            timetableEntity.setTimetableDate(LocalDate.from(LocalTime.parse(timetable.getTimetableDate())));
        }
        throw new RuntimeException("Топологии не существует: ");
    }
}
