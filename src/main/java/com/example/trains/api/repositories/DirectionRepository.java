package com.example.trains.api.repositories;

import com.example.trains.api.entities.DirectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<DirectionEntity, Long> {
}
