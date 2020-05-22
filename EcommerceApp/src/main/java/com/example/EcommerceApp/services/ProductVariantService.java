package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.ProductVariant;
import com.example.EcommerceApp.entities.Product_Variation;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.ProductVariantRepo;
import com.example.EcommerceApp.repositories.ProductVariationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    Logger logger = LoggerFactory.getLogger(ProductVariantService.class);

    /**
     * This method is  used to auto update the variant quantity
     */
    public void autoUpdateVariationQuantity() {

        Iterable<Product_Variation> variations = productVariationRepo.findAll();
        Iterator<Product_Variation> variationsIterator = variations.iterator();

        while (variationsIterator.hasNext()) {
            Product_Variation productVariation = variationsIterator.next();
            Long vid = productVariation.getProduct_variant_id();

            //fetching variant from RedisDb
            Optional<ProductVariant> optionalProductVariant = productVariantRepo.findById(vid.toString());

            if (optionalProductVariant.isPresent()) {

                ProductVariant productVariant = optionalProductVariant.get();

                // checking Quantity from Redis
                String qty = productVariant.getQuantity();

                // update Qty from Redis
                productVariation.setQuantity(Integer.parseInt(qty));

                // saving the Updated values in DB too
                productVariationRepo.save(productVariation);

                logger.info("Variant Quantity Updated from RedisDb and Stored in MySql Variant");

            }
            else {
                logger.error("Unable to find particular Variant from RedisDb for selected MySql Product Variation ");
            }
        }

    }

    /**
     * This method is used to store the variant
     * @param id
     * @return
     */
    public String saveVariant(Long id) {

        Optional<Product_Variation> product_variation = productVariationRepo.findById(id);
        if (product_variation.isPresent())
        {
            Product_Variation productVariation = product_variation.get();

            ProductVariant productVariant = new ProductVariant();

            Long vid = productVariation.getProduct_variant_id();
            Integer qty = productVariation.getQuantity();

            productVariant.setVid(vid.toString());
            productVariant.setQuantity(qty.toString());

            productVariantRepo.save(productVariant);

            return "Variant Saved Successfully";


        }
        else {
            throw new NotFoundException("Invalid Product-Variation id");
        }
    }

    /**
     * This method is used to store the quantity
     * @param variantId
     * @param qty
     * @return
     */
    @Transactional
    @Modifying
    public String updateStoredQuantity (Long variantId, Integer qty) {

        Optional<ProductVariant> optionalProductVariant = productVariantRepo.findById(variantId.toString());
        if (optionalProductVariant.isPresent()) {

            ProductVariant productVariant = optionalProductVariant.get();
            productVariant.setQuantity(qty.toString());

            productVariantRepo.save(productVariant);

            return "Quantity updated for selected Variant";

        }
        else {
            throw new NotFoundException("Invalid Variant ID");
        }

    }


    public Product_Variation findVariant(Long variantId) {

        // fetching variant from MysqlDb
        Optional<Product_Variation> variant1 = productVariationRepo.findById(variantId);

        //fetching variant from RedisDb
        Optional<ProductVariant> optionalProductVariant = productVariantRepo.findById(variantId.toString());

        if (variant1.isPresent()) {
            Product_Variation productVariation = variant1.get();

            if (productVariation.isActive()) {

                if (optionalProductVariant.isPresent()) {

                    ProductVariant productVariant = optionalProductVariant.get();

                    // checking Quantity from Redis
                    String qty = productVariant.getQuantity();

                    // update Qty from Redis
                    productVariation.setQuantity(Integer.parseInt(qty));

                    // saving the Updated values in DB too
                    productVariationRepo.save(productVariation);

                    // returning Product-variation with updated values to user
                    return productVariation;

                }
                else {
                    throw new NotFoundException("Invalid Variant ID");
                }
            } else {
                throw new NotFoundException("Requested Variant is unavailable at the Moment");
            }
        } else {
            throw new NotFoundException("Invalid Variant ID");
        }
    }

    /**
     * This method is used to get the variant details
     * @param vid
     * @return
     */
    public ProductVariant getVariant(String vid) {

        Optional<ProductVariant> productVariant = productVariantRepo.findById(vid);
        if (productVariant.isPresent()) {
            return productVariant.get();
        }
        else {
            throw new NotFoundException("id is invalid");
        }
    }

}
