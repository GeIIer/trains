package com.example.trains.api.repositories;


import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {

    Optional<TimetableEntity> findByTimetableDateAndTopology(LocalDate date, TopologyEntity idTopology);

    Optional<TimetableEntity> findByIdTimetable(Long idTimetable);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "", nativeQuery = true)
//    void updateStatusTrue(Long idTopology);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM timetable te WHERE te.topology_id_topology IN (SELECT te.topology_id_topology FROM topology tp WHERE tp.id_topology = :id)", nativeQuery = true)
    void deleteAllByTopology(Long id);

    List<TimetableEntity> findAllByTopology(TopologyEntity topology);
}
