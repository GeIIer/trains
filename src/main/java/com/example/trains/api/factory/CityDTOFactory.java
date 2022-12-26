package com.example.trains.api.factory;

import com.example.trains.api.dto.CityDTO;
import com.example.trains.api.dto.CityDTOWithCount;
import com.example.trains.api.entities.CityEntity;
import org.springframework.stereotype.Component;

@Component
public class CityDTOFactory {
    public CityDTO makeCityDTO (CityEntity entity) {
        return  CityDTO.builder()
                .cityName(entity.getCityName())
                .build();
    }
}
