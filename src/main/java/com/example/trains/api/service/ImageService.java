package com.example.trains.api.service;

import com.example.trains.api.entities.ImageEntity;
import com.example.trains.api.entities.TrainEntity;
import com.example.trains.api.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void save(MultipartFile image, TrainEntity entity) throws IOException {
        ImageEntity fileEntity = new ImageEntity();
        fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        fileEntity.setContentType(image.getContentType());
        fileEntity.setData(image.getBytes());
        fileEntity.setSize(image.getSize());

        //imageRepository.save(fileEntity);
    }

}
