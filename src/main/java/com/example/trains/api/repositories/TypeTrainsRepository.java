package com.example.trains.api.repositories;

import com.example.trains.api.entities.TypeTrainsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeTrainsRepository extends JpaRepository<TypeTrainsEntity, Long> {

    Optional<TypeTrainsEntity> findByTypeTrain(String type);

    boolean existsByTypeTrain (String type);
}
