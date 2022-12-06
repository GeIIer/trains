package com.example.trains.api.controllers;

import com.example.trains.api.topologyFile.Cell;
import com.example.trains.api.topologyFile.Step;
import com.example.trains.api.topologyFile.TopologyFileDTO;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import com.example.trains.api.service.FindWayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/ways"})
public class FindWayController {
    @Autowired
    private final TopologyRepository topologyRepository;
    @Autowired
    private final FindWayService findWayService;
    @Autowired
    private final FileService fileService;
    private static final String GET_ALL_WAYS = "";

    @GetMapping(GET_ALL_WAYS)
    ArrayList<Step> getAllWays(@RequestParam("idTopology") Long idTopology) {
        try {
            TopologyEntity topologyEntity = topologyRepository.findByIdTopology(idTopology);
            TopologyFileDTO topology = fileService.loadTopology(topologyEntity.getFilename());
            if (topology != null) {
                return findWayService.getWay(new Step(0,0,2), topology.getCell(15, 4), new ArrayList<Cell>(List.of(topology.getCell(11, 4))), topology);
            }
            throw new RuntimeException();
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException();
    }
}
