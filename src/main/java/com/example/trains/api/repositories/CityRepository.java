package com.example.trains.api.repositories;

import com.example.trains.api.dto.CityDTO;
import com.example.trains.api.dto.CityDTOWithCount;
import com.example.trains.api.dto.CityWithCount;
import com.example.trains.api.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByCityName(String cityName);

    @Query(value = "()", nativeQuery = true)
    List<CityEntity> findCityWithoutTopology();

    @Query(value = "SELECT id_city, city_name, count(*) as \"count\" FROM city\n" +
            "RIGHT JOIN topology t on city.id_city = t.city_id_city\n" +
            "group by id_city, city_name", nativeQuery = true)
    List<CityDTOWithCount> findCityWithTopology();
}
