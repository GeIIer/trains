package com.example.trains.api.controllers;

import com.example.trains.api.entities.TrainEntity;
import com.example.trains.api.entities.TypeTrainsEntity;
import com.example.trains.api.repositories.TrainRepository;
import com.example.trains.api.repositories.TypeTrainsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/train"})
public class TrainController {

    @Autowired
    private final TrainRepository trainRepository;
    @Autowired
    private final TypeTrainsRepository typeTrainsRepository;

    private static final String GET_ALL_TRAINS = "";

    private static final String GET_ALL_TYPE_TRAINS = "";

    private static final String ADD_TRAIN = "";

    private static final String DELETE_TRAIN = "/{idTrain}";

    @RequestMapping(GET_ALL_TRAINS)
    public List<TrainEntity> getAllTrains() {
        return trainRepository.findAll();
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
            }
        }
        else {
            throw new RuntimeException("Такой поезд уже существует");
        }
    }

    @DeleteMapping(DELETE_TRAIN)
    public ResponseEntity<String> deleteTrain (@PathVariable("idTrain") Long idTrain) {
        try {
            Optional<TrainEntity> optionalTrainEntity = trainRepository.findById(idTrain);
            if (optionalTrainEntity.isEmpty()) {
                return new ResponseEntity<>("Такого расписания нет", HttpStatus.OK);
            }
            TrainEntity train = optionalTrainEntity.get();
            trainRepository.delete(train);
            return new ResponseEntity<>("Удаление расписания произошло успешно", HttpStatus.OK);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return new ResponseEntity<>("Произошла ошибка при удалении", HttpStatus.OK);
    }
}
