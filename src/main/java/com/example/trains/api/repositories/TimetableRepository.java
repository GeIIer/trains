package com.example.trains.api.repositories;


import com.example.trains.api.entities.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {

    @Query(value = "SELECT * FROM timetable t WHERE t.timetable_date = :date AND topology_id_topology IN \n" +
            "(SELECT topology_id_topology FROM topology top WHERE top.id_topology = :idTopology)", nativeQuery = true)
    Optional<TimetableEntity> findByTimetableDateAndIdTopology (LocalDate date, Long idTopology);

    ArrayList<TimetableEntity> findAllByTopology(Long idTopology);
}
