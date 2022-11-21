package com.example.trains.api.service;

import com.example.trains.api.dto.TopologyFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию");
        }
    }

    public void save(TopologyFileDTO topology) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(root.toString() + "/" + topology.getTitle() + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(topology);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    public TopologyFileDTO load (String fileName) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            FileInputStream fileInputStream = new FileInputStream(root.toString() + "/" + fileName + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (TopologyFileDTO) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
}
