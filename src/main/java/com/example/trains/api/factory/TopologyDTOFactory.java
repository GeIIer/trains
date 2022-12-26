package com.example.trains.api.factory;

import com.example.trains.api.dto.TopologyDTO;
import com.example.trains.api.entities.TopologyEntity;
import org.springframework.stereotype.Component;

@Component
public class TopologyDTOFactory {
    public TopologyDTO makeTopologyDTO (TopologyEntity entity) {
        return  TopologyDTO.builder()
                .idTopology(entity.getIdTopology())
                .topologyName(entity.getTopologyName())
                .accountName(entity.getAccount().getName())
                .cityName(entity.getCity().getCityName())
                .build();
    }
}
