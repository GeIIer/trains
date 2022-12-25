package com.example.trains.api.controllers;

import com.example.trains.api.entities.CityEntity;
import com.example.trains.api.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/city"})
public class CityController {
    @Autowired
    private final CityRepository cityRepository;

    private static final String GET_ALL_CITY = "";

    private static final String ADD_CITY = "";

    @RequestMapping(GET_ALL_CITY)
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    @PostMapping(ADD_CITY)
    public void addCity(@RequestBody CityEntity city) {
        Optional<CityEntity> optionalCityEntity = cityRepository.findByCityName(city.getCityName());
        if (optionalCityEntity.isEmpty()) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setCityName(city.getCityName());
            cityRepository.save(cityEntity);
        }
        else {
            throw new RuntimeException("Такой город уже существует");
        }
    }

}
