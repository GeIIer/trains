package com.example.trains.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping({"/api/topology"})
public class TopologyController {

    private static final String UPLOAD_FILE = "";

    @PostMapping(UPLOAD_FILE)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return null;
    }
}
