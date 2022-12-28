package com.example.trains.api.controllers;

import com.example.trains.api.dto.*;
import com.example.trains.api.entities.CityEntity;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.factory.CityDTOFactory;
import com.example.trains.api.factory.TopologyDTOFactory;
import com.example.trains.api.factory.TrainDTOFactory;
import com.example.trains.api.repositories.CityRepository;
import com.example.trains.api.repositories.TimetableRepository;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.repositories.TrainRepository;
import com.example.trains.api.service.FileService;
import com.example.trains.api.service.FindWayService;
import com.example.trains.api.service.TopologyFileService;
import com.example.trains.api.timetableFile.PlatesAndInOut;
import com.example.trains.api.timetableFile.PlatesAndInOutSerializer;
import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.*;
import com.example.trains.authorization.entities.AccountEntity;
import com.example.trains.authorization.repositories.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
@RequestMapping({"/api/topology"})
public class TopologyController {
    @Autowired
    private final TopologyRepository topologyRepository;
    @Autowired
    private final TimetableRepository timetableRepository;
    @Autowired
    private final TrainRepository trainRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final CityRepository cityRepository;
    @Autowired
    private final TopologyDTOFactory topologyDTOFactory;
    @Autowired
    private final TrainDTOFactory trainDTOFactory;
    @Autowired
    private final CityDTOFactory cityDTOFactory;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final FindWayService findWayService;
    @Autowired
    private final TopologyFileService topologyFileService;

    private static final String CREATE_TOPOLOGY = "/create";
    private static final String UPLOAD_TOPOLOGY = "";
    private static final String DOWNLOAD_TOPOLOGY = "";
    private static final String GET_TOPOLOGY = "/bycity";
    private static final String GET_ALL_TOPOLOGY = "/all";
    private static final String GET_ALL_PLATES = "/plates";
    private static final String GET_TOPOLOGY_AND_RECORDS = "/{idTopology}/{date}";

    private static final String DELETE_TOPOLOGY = "";

    @PostMapping(CREATE_TOPOLOGY)
    public void createTopology(@RequestParam("topologyName") String topologyName,
                               @RequestParam("accountName") String accountName,
                               @RequestParam("city") String city,
                               @RequestBody String matrix) {
        try {
            Optional<AccountEntity> optionalAccountEntity = accountRepository.findByName(accountName);
            if (optionalAccountEntity.isPresent()) {
                Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByTopologyNameAndAccount(topologyName, optionalAccountEntity.get());
                if (optionalTopologyEntity.isPresent()) {
                    throw new RuntimeException("Такая топология уже существует: ");
                } else {
                    Optional<CityEntity> cityEntity = cityRepository.findByCityName(city);
                    if (cityEntity.isPresent()) {
                        TopologyEntity topologyEntity = new TopologyEntity();
                        topologyEntity.setTopologyName(topologyName);
                        topologyEntity.setCity(cityEntity.get());
                        topologyEntity.setAccount(optionalAccountEntity.get());
                        topologyEntity.setFilename(topologyName + accountName);

                        ObjectMapper mapper = new ObjectMapper();
                        TopologyFileDTO topology = mapper.readValue(matrix, TopologyFileDTO.class);
                        fileService.saveTopology(topologyEntity, topology);

                        topologyRepository.save(topologyEntity);
                    } else {
                        throw new RuntimeException("Такого города нет ");
                    }
                }
            }
            else {
                throw new RuntimeException("Пользователь не зарегистрирован ");
            }
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @PostMapping(UPLOAD_TOPOLOGY) //TODO Обновление статуса расписаний при изменении
    public String uploadTopology(@RequestParam("idTopology") Long idTopology,
                                 @RequestBody String matrix) {
        try {
            Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
            if (optionalTopologyEntity.isPresent()) {
                TopologyEntity topologyEntity = optionalTopologyEntity.get();
                ObjectMapper mapper = new ObjectMapper();
                TopologyFileDTO topology = mapper.readValue(matrix, TopologyFileDTO.class);
                fileService.saveTopology(topologyEntity, topology);
                //timetableRepository.updateStatusTrue(topologyEntity.getIdTopology());
                System.out.println("Топология загружена");
                System.out.println(topology.getTitle());
                System.out.println(topology.getBody());
                return "Топология загружена";
            }
            else {
                throw new RuntimeException("Такой топологии не существует:");
            }
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException("Ошибка:");
    }

    @GetMapping(DOWNLOAD_TOPOLOGY)
    public String downloadTopology(@RequestParam("idTopology") Long idTopology) {
        try {
                Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
                if (optionalTopologyEntity.isPresent())
                {
                    TopologyEntity topology = optionalTopologyEntity.get();
                    TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());

                    ObjectMapper mapper = new ObjectMapper();

                    SimpleModule module = new SimpleModule();
                    module.addSerializer(TopologyFileDTO.class, new TopologyFileDTOSerializer());
                    mapper.registerModule(module);
                    return mapper.writeValueAsString(topologyFileDTO);
                }
            throw new RuntimeException();
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException();
    }

    @GetMapping(GET_TOPOLOGY)
    public List<TopologyDTO> getTopologiesByCity(@RequestParam("cityName") String cityName) {
        Optional<CityEntity> city = cityRepository.findByCityName(cityName);
        if (city.isPresent()) {
            return (topologyRepository.findAllByCity(city.get())
                    .stream().map(topologyDTOFactory::makeTopologyDTO)
                    .collect(Collectors.toList()));
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такого города не существует");
        }
    }

    @GetMapping(GET_ALL_TOPOLOGY)
    public List<TopologyDTO> getAllTopology() {
        return (topologyRepository.findAll()
                .stream().map(topologyDTOFactory::makeTopologyDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping (GET_ALL_PLATES)
    public String getAllPlates(@RequestParam("idTopology") Long idTopology) {
        try {
            Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
            if (optionalTopologyEntity.isPresent())
            {
                TopologyEntity topology = optionalTopologyEntity.get();
                TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());
                ArrayList<Cell> inOut = topologyFileService.getInOut(topologyFileDTO.getBody());
                ArrayList<Plate> plates = topologyFileService.getPlates(topologyFileDTO.getBody());
                List<TrainDTO> trains = trainRepository.findAll()
                        .stream().map(trainDTOFactory::makeTrainDTO)
                        .collect(Collectors.toList());
                if (trains.size() == 0) {
                    System.err.println("Нет созданных поездов");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка: нет созданных поездов");
                }
                List<CityDTO> cityDTOS = cityRepository.findAll()
                        .stream().map(cityDTOFactory::makeCityDTO)
                        .collect(Collectors.toList());
                if (cityDTOS.size() == 0) {
                    System.err.println("Нет созданных городов");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка: нет созданных городов");
                }
                PlatesAndInOut platesAndInOut = new PlatesAndInOut(inOut, plates, trains, cityDTOS);
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(PlatesAndInOut.class, new PlatesAndInOutSerializer());
                try {
                    mapper.registerModule(module);
                    return mapper.writeValueAsString(platesAndInOut);
                }
                catch ( JsonProcessingException jsonProcessingException ) {
                    System.err.println(jsonProcessingException.getMessage() );
                    throw new RuntimeException( jsonProcessingException );
                }
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException("Ошибка: ");
    }

    @GetMapping(GET_TOPOLOGY_AND_RECORDS)
    public TopologyAndRecordsDTO getTopologyAndRecords (@PathVariable("idTopology") Long idTopology,
                                                        @PathVariable("date") String dateTimeString) {
        try {
            Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
            if (optionalTopologyEntity.isPresent()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(dateTimeString, formatter);
                Optional<TimetableEntity> optionalTimetableEntity = timetableRepository.findByTimetableDateAndTopology(date, optionalTopologyEntity.get());
                if (optionalTimetableEntity.isPresent()) {
                    TopologyEntity topology = optionalTopologyEntity.get();
                    TimetableEntity timetable = optionalTimetableEntity.get();
                    TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());
                    ArrayList<RecordAndWayDTO> recordAndWayDTOS = fileService.loadRecords(timetable.getFileName());
                    TopologyAndRecordsDTO topologyAndRecordsDTO = new TopologyAndRecordsDTO(topologyFileDTO.getBody(), recordAndWayDTOS);
                    return topologyAndRecordsDTO;
                } else {
                    throw new RuntimeException("Нет записей в расписании: ");
                }
            } else {
                throw new RuntimeException("Топологии не существует: ");
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException("Ошибка: ");
    }

    @DeleteMapping(DELETE_TOPOLOGY)
    public ResponseEntity<String> deleteTimetable (@RequestParam("idTopology") Long idTopology) {
        Optional<TopologyEntity> optionalTopologyEntity = topologyRepository.findByIdTopology(idTopology);
        if (optionalTopologyEntity.isEmpty()) {
            return new ResponseEntity<>("Такой топологии нет", HttpStatus.OK);
        }
        TopologyEntity topology = optionalTopologyEntity.get();
        timetableRepository.deleteAllByTopology(idTopology);
        topologyRepository.delete(topology);
        if (fileService.deleteTopology(topology.getFilename())) {
            return new ResponseEntity<>("Удаление топологии произошло успешно", HttpStatus.OK);
        }
        return new ResponseEntity<>("Произошла ошибка при удалении", HttpStatus.OK);
    }
}
