package com.example.trains.api.repositories;

import com.example.trains.api.entities.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {

    Optional<TrainEntity> findByNameTrain (String name);
}
