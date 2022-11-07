package com.example.trains.api.controllers;

import com.example.trains.api.dto.TopologyFileDTO;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/topology"})
public class TopologyController {

    @Autowired
    private final TopologyRepository topologyRepository;

    @Autowired
    private final FileService fileService;

    private static final String UPLOAD_TOPOLOGY = "";

    private static final String DOWNLOAD_TOPOLOGY = "";

    private static final String GET_ALL_TOPOLOGY = "/all";

    private static final Gson gson = new Gson();

    @PostMapping(UPLOAD_TOPOLOGY)
    public String uploadTopology(@RequestBody String file) {
        try {
            System.out.println(file);
            TopologyFileDTO topology = gson.fromJson(file, TopologyFileDTO.class);

            TopologyEntity newTopology = new TopologyEntity();
            fileService.save(topology);

            System.out.println(topology.getTitle());
            System.out.println(topology.getBody());
            return "Топология загружена";
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return "Ошибка";
    }

    @GetMapping(DOWNLOAD_TOPOLOGY)
    public String downloadTopology(@RequestBody String file) {
        try {
            System.out.println(file);
            TopologyFileDTO topology = gson.fromJson(file, TopologyFileDTO.class);
            System.out.println(topology.getTitle());
            System.out.println(topology.getBody());
            return "Файл загружен";
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return "Ошибка";
    }
}
