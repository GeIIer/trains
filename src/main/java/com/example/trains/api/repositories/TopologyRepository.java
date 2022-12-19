package com.example.trains.api.repositories;

import com.example.trains.api.entities.TopologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopologyRepository extends JpaRepository<TopologyEntity, Long> {

    boolean existsByIdTopology(Long idTopology);
    Optional<TopologyEntity> findByIdTopology(Long idTopology);

    Optional<TopologyEntity> findByTopologyNameAndAccount_Name(String topologyName, String accountName);
}
