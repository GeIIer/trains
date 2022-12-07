package com.example.trains.api.controllers;

import com.example.trains.api.dto.*;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.factory.TopologyDTOFactory;
import com.example.trains.api.repositories.TopologyRepository;
import com.example.trains.api.service.FileService;
import com.example.trains.api.service.TopologyFileService;
import com.example.trains.api.timetableFile.PlatesAndInOut;
import com.example.trains.api.timetableFile.PlatesAndInOutSerializer;
import com.example.trains.api.topologyFile.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
    @Autowired
    private final TopologyFileService topologyFileService;

    private static final String UPLOAD_TOPOLOGY = "";

    private static final String DOWNLOAD_TOPOLOGY = "";

    private static final String GET_ALL_TOPOLOGY = "/all";

    private static final String GET_ALL_PLATES = "/plates";


    @PostMapping(UPLOAD_TOPOLOGY) //TODO
    public String uploadTopology(@RequestBody String file) {
        try {
            System.out.println(file);
            ObjectMapper mapper = new ObjectMapper();
            TopologyFileDTO topology = mapper.readValue(file, TopologyFileDTO.class);
            TopologyEntity newTopology = new TopologyEntity();
            fileService.saveTopology(topology);
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
    public String downloadTopology(@RequestParam("idTopology") Long idTopology) {
        try {
                TopologyEntity topology = topologyRepository.findByIdTopology(idTopology);
                if (topology != null) {
                    TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());

                    ObjectMapper mapper = new ObjectMapper();

                    SimpleModule module = new SimpleModule();
                    module.addSerializer(TopologyFileDTO.class, new TopologyFileDTOSerializer());
                    mapper.registerModule(module);

                    return mapper.writeValueAsString(topologyFileDTO);
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

    @GetMapping (GET_ALL_PLATES)
    public PlatesAndInOut getAllPlates(@RequestParam("idTopology") Long idTopology) {
        try {
            TopologyEntity topology = topologyRepository.findByIdTopology(idTopology);
            if (topology != null) {
                TopologyFileDTO topologyFileDTO = fileService.loadTopology(topology.getFilename());
                ArrayList<Cell> inOut = topologyFileService.getInOut(topologyFileDTO.getBody());
                ArrayList<Plate> plates = topologyFileService.getPlates(topologyFileDTO.getBody());
                PlatesAndInOut platesAndInOut = new PlatesAndInOut(inOut, plates);
//            ObjectMapper mapper = new ObjectMapper();
//            SimpleModule module = new SimpleModule();
//            module.addSerializer(PlatesAndInOut.class, new PlatesAndInOutSerializer());
//            try {
//                mapper.registerModule(module);
//                String json = mapper.writeValueAsString( platesAndInOut );
//                return json;
//            }
//            catch ( JsonProcessingException jsonProcessingException ) {
//                System.err.println(jsonProcessingException.getMessage() );
//                throw new RuntimeException( jsonProcessingException );
//            }
                return platesAndInOut;
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        throw new RuntimeException("GBYFC");
    }
}
