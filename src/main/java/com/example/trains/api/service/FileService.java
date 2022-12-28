package com.example.trains.api.service;

import com.example.trains.api.dto.RecordAndWayDTO;
import com.example.trains.api.entities.TimetableEntity;
import com.example.trains.api.entities.TopologyEntity;
import com.example.trains.api.timetableFile.Record;
import com.example.trains.api.topologyFile.TopologyFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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

    public void saveTopology(TopologyEntity topologyEntity, TopologyFileDTO topology) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Path topologyPath = Paths.get(uploadPath + "/" + topologyEntity.getFilename());
            Files.createDirectories(topologyPath);
            FileOutputStream fileOutputStream = new FileOutputStream(topologyPath.toString() + "/" + topologyEntity.getFilename() + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(topology);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
    public void saveTimetable (TimetableEntity timetableEntity, ArrayList<RecordAndWayDTO> recordAndWayDTOS) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            Path filePath = Paths.get(uploadPath + "/" + timetableEntity.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(filePath.toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recordAndWayDTOS);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    public TopologyFileDTO loadTopology(String fileName) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            Path topologyPath = Paths.get(uploadPath + "/" + fileName);
            if (!Files.exists(topologyPath)) {
                throw new RuntimeException("Ошибка: Такой топологии нет");
            }
            FileInputStream fileInputStream = new FileInputStream(topologyPath.toString() + "/" + fileName + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            TopologyFileDTO topologyFileDTO = (TopologyFileDTO) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return topologyFileDTO;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
    public ArrayList<RecordAndWayDTO> loadRecords (String fileName) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            Path recordsPath = Paths.get(uploadPath + "/" + fileName);
            if (!Files.exists(recordsPath)) {
                throw new RuntimeException("Ошибка: Такого расписания нет");
            }
            FileInputStream fileInputStream = new FileInputStream(recordsPath.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<RecordAndWayDTO> recordAndWayDTOS = (ArrayList<RecordAndWayDTO>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return recordAndWayDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    public boolean deleteTimetable (String deletePath) {
        try {
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            File file = new File(path + "/" + deletePath);
            return file.delete();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    public boolean deleteTopology(String deletePath) {
        try {
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) {
                throw new RuntimeException("Ошибка: Такой директории нет");
            }
            File file = new File(path + "/" + deletePath);
            String[] entries = file.list();
            if (entries != null) {
                for (String s : entries) {
                    File currentFile = new File(file.getPath(), s);
                    currentFile.delete();
                }
            }
            return file.delete();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }
}
