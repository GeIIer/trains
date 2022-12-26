package com.example.trains.api.controllers;

import com.example.trains.api.dto.TrainDTO;
import com.example.trains.api.entities.TrainEntity;
import com.example.trains.api.entities.TypeTrainsEntity;
import com.example.trains.api.factory.TrainDTOFactory;
import com.example.trains.api.repositories.TrainRepository;
import com.example.trains.api.repositories.TypeTrainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/train"})
public class TrainController {

    @Autowired
    private final TrainRepository trainRepository;
    @Autowired
    private final TypeTrainsRepository typeTrainsRepository;
    @Autowired
    private final TrainDTOFactory trainDTOFactory;
    private static final String GET_ALL_TRAINS = "";

    private static final String GET_ALL_TYPE_TRAINS = "/type";

    private static final String ADD_TRAIN = "";

    private static final String DELETE_TRAIN = "";

    @RequestMapping(GET_ALL_TRAINS)
    public List<TrainDTO> getAllTrains() {
        return (trainRepository.findAll()
                .stream().map(trainDTOFactory::makeTrainDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(GET_ALL_TYPE_TRAINS)
    public List<TypeTrainsEntity> getAllTypeTrains() {
        return typeTrainsRepository.findAll();
    }

    @PostMapping(ADD_TRAIN)
    public void addTrain(@RequestBody TrainEntity train) {
        Optional<TrainEntity> optionalTrainEntity = trainRepository.findByNameTrain(train.getNameTrain());
        if (optionalTrainEntity.isEmpty()) {
            if (typeTrainsRepository.existsByTypeTrain(train.getTypeTrain().getTypeTrain())) {
                TrainEntity trainEntity = new TrainEntity();
                trainEntity.setNameTrain(train.getNameTrain());
                trainEntity.setNumberOfWagons(train.getNumberOfWagons());
                trainEntity.setTypeTrain(train.getTypeTrain());
                trainRepository.save(trainEntity);
            }
        }
        else {
            throw new RuntimeException("Такой поезд уже существует");
        }
    }

    @DeleteMapping(DELETE_TRAIN)
    public ResponseEntity<String> deleteTrain (@RequestParam("idTrain") Long idTrain) {
        try {
            Optional<TrainEntity> optionalTrainEntity = trainRepository.findById(idTrain);
            if (optionalTrainEntity.isEmpty()) {
                return new ResponseEntity<>("Такого поезда нет", HttpStatus.OK);
            }
            TrainEntity train = optionalTrainEntity.get();
            trainRepository.delete(train);
            return new ResponseEntity<>("Удаление поезда произошло успешно", HttpStatus.OK);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return new ResponseEntity<>("Произошла ошибка при удалении", HttpStatus.OK);
    }
}
