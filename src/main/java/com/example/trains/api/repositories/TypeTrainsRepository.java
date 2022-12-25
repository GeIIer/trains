package com.example.trains.api.repositories;

import com.example.trains.api.entities.TypeTrainsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTrainsRepository extends JpaRepository<TypeTrainsEntity, Long> {

    boolean existsByTypeTrain (String type);
}
