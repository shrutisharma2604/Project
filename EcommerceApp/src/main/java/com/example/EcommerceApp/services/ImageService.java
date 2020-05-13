package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.Image;
import com.example.EcommerceApp.entities.Product;
import com.example.EcommerceApp.entities.Product_Variation;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.ImageRepository;
import com.example.EcommerceApp.repositories.ProductRepository;
import com.example.EcommerceApp.repositories.ProductVariationRepo;
import com.example.EcommerceApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepo productVariationRepo;


    public Optional<User> getLoggedInUser(Long id) {
        Optional<User> user=userRepository.findById(id);
        return user;

    }
    public String saveImage(Image image, Long uid) {

        Optional<User> optionalUser = userRepository.findById(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            imageRepository.save(image);

            user.setImageId(image.getId());

            userRepository.save(user);

            return "Image Uploaded";

        }
        else
        {
            throw new NotFoundException("Invalid User");
        }
    }

    public String saveProductImage(Image image, Long pid) {

        Optional<Product> optionalProduct = productRepository.findById(pid);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            imageRepository.save(image);

            product.setImageId(image.getId());
            productRepository.save(product);

            return "Product Image Uploaded";

        }else
            throw new NotFoundException("Product Not found");
    }

    public String saveProductVariationImage(Image image,Long vid){
        Optional<Product_Variation> product_variation=productVariationRepo.findById(vid);
        if (product_variation.isPresent()){
            Product_Variation product_variation1=product_variation.get();
            imageRepository.save(image);
            product_variation1.setImageId(image.getId());
            productVariationRepo.save(product_variation1);
            return "Product Variation Image uploaded";
        }
        else
            throw new NotFoundException("Product Variation Not found");
    }

    public Image downloadUserImage(Long fileId){
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found with id " + fileId));
    }
}
