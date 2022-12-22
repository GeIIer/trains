package com.example.trains.api.repositories;


import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {

    Optional<TimetableEntity> findByTimetableDateAndTopology(LocalDate date, TopologyEntity idTopology);

    Optional<TimetableEntity> findByIdTimetable(Long idTimetable);

    List<TimetableEntity> findAllByTopology(TopologyEntity topology);
}
