package com.example.trains.api.controllers;

import com.example.trains.api.dto.*;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.factory.TopologyDTOFactory;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/topology"})
public class TopologyController {
    @Autowired
    private final TopologyRepository topologyRepository;
    @Autowired
    private final TopologyDTOFactory topologyDTOFactory;
    @Autowired
    private final FileService fileService;

    private static final String UPLOAD_TOPOLOGY = "";

    private static final String DOWNLOAD_TOPOLOGY = "";

    private static final String GET_ALL_TOPOLOGY = "/all";


    @PostMapping(UPLOAD_TOPOLOGY)
    public String uploadTopology(@RequestBody String file) {
        try {
            System.out.println(file);
            ObjectMapper mapper = new ObjectMapper();
            TopologyFileDTO topology = mapper.readValue(file, TopologyFileDTO.class);
            TopologyEntity newTopology = new TopologyEntity();
            fileService.save(topology);
            System.out.println("Топология загружена");
            System.out.println(topology.getTitle());
            System.out.println(topology.getBody());
            return "Топология загружена";
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException("Ошибка:");
    }

    @GetMapping(DOWNLOAD_TOPOLOGY)
    public ArrayList<ArrayList<Cell>> downloadTopology(@RequestParam("idTopology") Long idTopology) {
        try {
                TopologyEntity topology = topologyRepository.findByIdTopology(idTopology);
                if (topology != null) {
                    return fileService.load(topology.getFilename()).getBody();
            }
            throw new RuntimeException();
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException();
    }

    @GetMapping(GET_ALL_TOPOLOGY)
    public List<TopologyDTO> getAllTopology() {
        return (topologyRepository.findAll()
                .stream().map(topologyDTOFactory::makeTopologyDTO)
                .collect(Collectors.toList()));
    }
}
