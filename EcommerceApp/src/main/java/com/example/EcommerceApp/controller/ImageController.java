package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.entities.Image;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.ImageRepository;
import com.example.EcommerceApp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping(path = "/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    ImageRepository imageRepository;

    private static String UPLOADED_FOLDER = "images/";

    @PostMapping(name = "/uploadImage")
    public Long uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
      //  User user = imageService.getLoggedInUser();
       if(file.isEmpty()){
           throw new NotFoundException("file not found");
       }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setPath(UPLOADED_FOLDER);
            image.setStatus(true);
            image.setCreatedOn(new Date());
         //   image.setCreatedBy(user);

            imageRepository.save(image);
            return image.getId();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
