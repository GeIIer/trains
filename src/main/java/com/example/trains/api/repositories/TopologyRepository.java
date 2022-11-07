package com.example.trains.api.repositories;

import com.example.trains.api.entities.TopologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopologyRepository extends JpaRepository<TopologyEntity, Long> {
}
