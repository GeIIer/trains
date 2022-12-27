package com.example.trains.api.controllers;

import com.example.trains.api.dto.CityDTO;
import com.example.trains.api.dto.CityDTOWithCount;
import com.example.trains.api.dto.CityWithCount;
import com.example.trains.api.entities.CityEntity;
import com.example.trains.api.factory.CityDTOFactory;
import com.example.trains.api.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/city"})
public class CityController {
    @Autowired
    private final CityRepository cityRepository;
    @Autowired
    private final CityDTOFactory cityDTOFactory;
    private static final String GET_ALL_CITY = "";

    private static final String GET_ALL_CITY_WITH_TOPOLOGY = "/with";

    private static final String GET_ALL_CITY_WITHOUT_TOPOLOGY = "/without";
    private static final String ADD_CITY = "";

    @RequestMapping(GET_ALL_CITY)
    public List<CityDTO> getAllCities() {
        return (cityRepository.findAll()
                .stream().map(cityDTOFactory::makeCityDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(GET_ALL_CITY_WITHOUT_TOPOLOGY)
    public List<CityDTO> getAllCitiesWithoutTopology() {
        return (cityRepository.findCityWithoutTopology()
                .stream().map(cityDTOFactory::makeCityDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(GET_ALL_CITY_WITH_TOPOLOGY)
    public List<CityWithCount> getAllCitiesWithTopology() {
        List<CityDTOWithCount> cities = cityRepository.findCityWithTopology();
        List<CityWithCount> cityWithCounts = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            CityWithCount city = new CityWithCount();
            city.setCityName(cities.get(i).getCity_name());
            city.setCount(cities.get(i).getCount());
            cityWithCounts.add(city);
        }
        return cityWithCounts;
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
