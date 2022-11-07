package com.example.trains.api.repositories;


import com.example.trains.api.entities.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {
}
