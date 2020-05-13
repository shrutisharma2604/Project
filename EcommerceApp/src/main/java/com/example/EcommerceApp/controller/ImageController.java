package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.entities.Image;
import com.example.EcommerceApp.entities.Product;
import com.example.EcommerceApp.entities.Product_Variation;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.BadRequestException;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.ImageRepository;
import com.example.EcommerceApp.repositories.ProductRepository;
import com.example.EcommerceApp.repositories.ProductVariationRepo;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    Logger logger = LoggerFactory.getLogger(ImageController.class);

    private static String UPLOADED_FOLDER = "/home/shruti/Documents/EcommerceApp/src/main/resources/static/users/";
    private static String UPLOAD_FOLDER = "/home/shruti/Documents/EcommerceApp/src/main/resources/static/products/";


    @PostMapping(path = "/uploadImage/{id}")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file,@PathVariable("id") Long id) throws IOException {

        Optional<User> user=imageService.getLoggedInUser(id);
        Long uid=user.get().getId();

        if (file.isEmpty()) {
            throw new NotFoundException("file not found");
        }
        try {
            String fileExtension = null;

            byte[] bytes = file.getBytes();

            fileExtension = file.getOriginalFilename().split("\\.")[1];

            if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                    || fileExtension.equals("png") || fileExtension.equals("bmp")) {
                Path path = Paths.get(UPLOADED_FOLDER+ uid + "." + fileExtension);
                Files.write(path,bytes);

                Image image = new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
                image.setFileName(uid.toString() + "." + fileExtension);
                image.setPath(path.toString());
                image.setCreatedOn(new Date());
                image.setUserId(uid);
                image.setStatus(true);

                String message = imageService.saveImage(image, uid);

                return new ResponseEntity<>(message, HttpStatus.CREATED);
            } else {
                throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                        " for uploading image");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping(path = "/uploadProductImage/{id}/{pid}")
    public ResponseEntity<Object> uploadProductImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable("pid") Long pid,@PathVariable("id") Long id ) throws IOException {

        if (file.isEmpty()) {
            throw new NotFoundException("Upload Image");
        }

        Optional<User> seller = imageService.getLoggedInUser(id);
        Long sellerId = seller.get().getId();

        Optional<Product> optionalProduct = productRepository.findById(pid);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Long sid = product.getSeller().getId();

            if (sid.equals(sellerId)) {
                try {
                    String fileExtension = null;

                    byte[] bytes = file.getBytes();

                    fileExtension = file.getOriginalFilename().split("\\.")[1];

                    if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                            || fileExtension.equals("png") || fileExtension.equals("bmp")) {

                        Path path = Paths.get(UPLOAD_FOLDER + pid + "." + fileExtension);
                        Files.write(path, bytes);

                        Image image = new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
                        image.setFileName(pid.toString() + "." + fileExtension);
                        image.setPath(path.toString());
                        image.setCreatedOn(new Date());
                        image.setUserId(sellerId);

                        String message = imageService.saveProductImage(image, pid);

                        return new ResponseEntity<>(message, HttpStatus.CREATED);

                    } else {
                        throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                                " for uploading image");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }

            } else {
                throw new BadRequestException("Product not associated to Logged in Seller");
            }

        } else {
            throw new NotFoundException("Product not found with requested ID");
        }
    }

    @GetMapping(path = "/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // Load file from database
        Image image = imageService.downloadUserImage(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }


    @PostMapping("/product/variation/uploadImage/{vid}/{id}")
    public ResponseEntity<Object> uploadProductVariationImage(@RequestParam("file") MultipartFile file,
                                                              @PathVariable Long vid,@PathVariable("id") Long id) throws IOException {

        if (file.isEmpty()) {
            throw new NotFoundException("Upload Image");
        }

        Optional<User> seller = imageService.getLoggedInUser(id);
        Long sellerId = seller.get().getId();

        Optional<Product_Variation> product_variation = productVariationRepo.findById(vid);
        if (product_variation.isPresent()) {

            Product_Variation productVariation = product_variation.get();
            Long pid = productVariation.getProduct().getId();
            Long sid = productVariation.getProduct().getSeller().getId();

            Optional<Product> product = productRepository.findById(pid);
            if (product.isPresent()) {

                if (sid.equals(sellerId)) {

                    try {
                        String fileExtension = null;

                        byte[] bytes = file.getBytes();

                        fileExtension = file.getOriginalFilename().split("\\.")[1];

                        if (fileExtension.equals("jpeg") || fileExtension.equals("jpg")
                                || fileExtension.equals("png") || fileExtension.equals("bmp")) {

                            File newPath = new File(UPLOAD_FOLDER + pid + "/variations");
                            if (!newPath.exists()) {
                                if (newPath.mkdir()) {
                                    logger.info("New Directory Created");
                                } else {
                                    logger.error("Failed to create a new directory");
                                }
                            }

                            Path path = Paths.get(newPath.toString() + "/" + vid + "." + fileExtension);
                            Files.write(path, bytes);


                            Image image = new Image(file.getOriginalFilename(),file.getContentType(),file.getBytes());
                            image.setFileName(pid.toString() + "." + fileExtension);
                            image.setPath(path.toString());
                            image.setCreatedOn(new Date());
                            image.setUserId(sellerId);

                            String message = imageService.saveProductVariationImage(image, vid);

                            return new ResponseEntity<>(message, HttpStatus.CREATED);

                        } else {
                            throw new BadRequestException("Invalid file format, Kindly use .jpg, .jpeg, png, .bmp format" +
                                    " for uploading image");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                } else {
                    throw new BadRequestException("Product not associated to Logged in Seller");
                }

            } else {
                throw new NotFoundException("Product not found with requested ID");
            }
        } else {
            throw new NotFoundException("Invalid id");
        }
    }
}
