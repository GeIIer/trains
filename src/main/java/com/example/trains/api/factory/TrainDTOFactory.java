package com.example.trains.api.factory;

import com.example.trains.api.dto.TrainDTO;
import com.example.trains.api.entities.TrainEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainDTOFactory {
    public TrainDTO makeTrainDTO (TrainEntity entity) {
        return  TrainDTO.builder()
                .idTrain(entity.getIdTrain())
                .nameTrain(entity.getNameTrain())
                .typeTrain(entity.getTypeTrain().getTypeTrain())
                .numberOfWagons(entity.getNumberOfWagons())
                .build();
    }
}
