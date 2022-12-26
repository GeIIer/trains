package com.example.trains.api.repositories;

import com.example.trains.api.entities.CityEntity;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.authorization.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopologyRepository extends JpaRepository<TopologyEntity, Long> {

    boolean existsByIdTopology(Long idTopology);
    Optional<TopologyEntity> findByIdTopology(Long idTopology);

    List<TopologyEntity> findAllByCity(CityEntity city);

    Optional<TopologyEntity> findByTopologyNameAndAccount(String topology, AccountEntity account);
}
