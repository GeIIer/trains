package com.example.trains.api.repositories;

import com.example.trains.api.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    CityEntity findByCityName(String cityName);
}
