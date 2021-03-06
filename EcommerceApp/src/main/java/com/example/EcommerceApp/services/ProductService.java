package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.exception.BadRequestException;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * This method is used to add the product
     * @param sellerId
     * @param categoryId
     * @param productDto
     * @return
     */
   public String addProduct(Long sellerId, Long categoryId, ProductDTO productDto)  {

        Optional<Category> category = categoryRepository.findById(categoryId);

        Optional<Seller> seller = sellerRepository.findById(sellerId);

        if (category.isPresent()) {

            Long productId = productRepository.findUniqueProduct(String.valueOf(productDto.getBrand()), categoryId, sellerId, productDto.getName());

            if (productId == null) {
                Product product = new Product();

                product.setBrand(productDto.getBrand());
                product.setCategory(category.get());
                product.setSeller(seller.get());
                product.setName(productDto.getName());
                product.setDescription(productDto.getDescription());
                product.setActive(true);
                product.setDeleted(false);

                productRepository.save(product);

                emailNotificationService.sendNotification("Activate Product", "Product name is : " + productDto.getName() + " and seller is : " + seller.get().getCompanyName() + " and product id is : " + product.getId(), "pallavisharma3126@gmail.com");

                return "Product added successfully";
            } else {
                throw new BadRequestException("Product name already exist");
            }

        } else {
            throw new NotFoundException("Category not found");
        }
    }

    /**
     * This method is used to add the product variations
     * @param productId
     * @param productVariationDto
     * @return
     */
    public String addProductVariation(Long productId, ProductVariationDTO productVariationDto)  {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            if (productVariationDto.getQuantity() > 0) {
                if (productVariationDto.getPrice() > 0) {
                    if (product.get().isActive() && !(product.get().isDeleted())) {
                        Product_Variation productVariation = new Product_Variation();
                        BeanUtils.copyProperties(productVariationDto, productVariation);
                        productVariation.setProduct(product.get());

                        ProductVariant productVariant=new ProductVariant();
                        Long vid=productVariationDto.getProduct_variant_id();
                        Integer qty=productVariationDto.getQuantity();
                        productVariant.setVid(vid.toString());
                        productVariant.setQuantity(qty.toString());
                        productVariantRepo.save(productVariant);
                        productVariationRepo.save(productVariation);

                    } else {
                        throw new BadRequestException("Product may be deleted or inactive");
                    }
                } else {
                    throw new BadRequestException("Price should be greater than 0");
                }

            } else {
                throw new BadRequestException("Quantity should be greater than 0");
            }
        } else {
            throw new NotFoundException("Product not found");
        }
        return "Variation saved";
    }

    /**
     * This method is used to get the product of a particular seller
     * @param userId
     * @param productId
     * @return
     */
    public ProductViewDTO getProduct(Long userId, Long productId)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            if (userId.equals(product.get().getSeller().getId())) {
                if (!(product.get().isDeleted())) {
                    //return product.get();
                    ProductViewDTO productViewDto = new ProductViewDTO();
                    BeanUtils.copyProperties(product.get(), productViewDto);

                    return productViewDto;
                } else {
                    throw new BadRequestException("Product is Deleted");
                }

            } else {
                throw new BadRequestException("You don't have authorization to view this product");
            }
        } else {
            throw new NotFoundException("Product not found");
        }

    }

    /**
     * This method  is used to get the variations of product
     * @param userId
     * @param variationId
     * @return
     */
    public ProductVariationGetDTO getProductVariation(Long userId, Long variationId)  {

        Optional<Product_Variation> productVariation = productVariationRepo.findById(variationId);

        if (productVariation.isPresent()) {
            if (userId.equals(productVariation.get().getProduct().getSeller().getId())) {
                if (!(productVariation.get().getProduct().isDeleted())) {

                    ProductVariationGetDTO productVariationGetDto = new ProductVariationGetDTO();

                    BeanUtils.copyProperties(productVariation.get(), productVariationGetDto);
                    productVariationGetDto.setProductId(productVariation.get().getProduct().getId());
                    productVariationGetDto.setProductName(productVariation.get().getProduct().getName());
                    productVariationGetDto.setImageId(productVariation.get().getProduct().getImageId());

                    return productVariationGetDto;

                } else {
                    throw new BadRequestException("Product is deleted");
                }
            } else {
                throw new BadRequestException("You don't have authorization to view this product");
            }

        } else {
            throw new NotFoundException("Product Variation not found with variation id : " + variationId);
        }

    }

    /**
     * This method is used to list all the products of particular seller
     * @param userId
     * @return
     */
    public Set<ProductViewDTO> getProducts(Long userId) {
        List<Product> products = productRepository.findAllProducts(userId);

        Set<ProductViewDTO> productViewDTOSet = new HashSet<>();

        for (Product product : products) {
            ProductViewDTO productViewDto = new ProductViewDTO();

            if (!(product.isDeleted())) {
                BeanUtils.copyProperties(product, productViewDto);

                productViewDto.setCompanyName(product.getSeller().getCompanyName());

                productViewDTOSet.add(productViewDto);
            }
        }
        if (productViewDTOSet.size() < 1) {
            throw new BadRequestException(" or product may be deleted");
        }
        return productViewDTOSet;
    }

    /**
     * This method is used to get all the variations of product
     * @param userId
     * @param productId
     * @return
     */
    public List<ProductVariationGetDTO> getProductVariations(Long userId, Long productId)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            if (userId.equals(product.get().getSeller().getId())) {
                if (!(product.get().isDeleted())) {
                    List<Product_Variation> productVariations = productVariationRepo.findByProductId(productId);

                    List<ProductVariationGetDTO> productVariationGetDTOS = new ArrayList<>();

                    for (Product_Variation productVariation1 : productVariations) {
                        ProductVariationGetDTO productVariationGetDto = new ProductVariationGetDTO();

                        BeanUtils.copyProperties(productVariation1, productVariationGetDto);
                        productVariationGetDto.setProductId(productVariation1.getProduct().getId());
                        productVariationGetDto.setProductName(productVariation1.getProduct().getName());

                        productVariationGetDTOS.add(productVariationGetDto);
                    }

                    return productVariationGetDTOS;

                } else {
                    throw new BadRequestException("Product may be deleted");
                }
            } else {
                throw new BadRequestException("You don't have authorization to view this product");
            }
        } else {
            throw new NotFoundException("Product not found for product id : " + productId);
        }
    }

    /**
     * This method is uused to delete a particular product
     * @param id
     * @return
     */
    @Transactional
    public String deleteProduct(Long id)  {
        if (!productRepository.findById(id).isPresent()) {
            return id + " product does not exist";
        }
        productRepository.deleteById(id);
        return "Product Deleted";

    }

    /**
     * This method is used to update the details of product
     * @param userId
     * @param productId
     * @param productViewDto
     * @return
     */
    public String updateProduct(Long userId, Long productId, ProductViewDTO productViewDto)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            if (userId.equals(product.get().getSeller().getId())) {
                product.get().setDescription(productViewDto.getDescription());
                product.get().setReturnable(productViewDto.isReturnable());
                product.get().setCancellable(productViewDto.isCancellable());
                product.get().setDeleted(productViewDto.isDeleted());

                productRepository.save(product.get());

                return "Product updated successfully";
            } else {
                throw new BadRequestException("You don't have authorization to update this product");
            }
        } else {
            throw new NotFoundException("Product not found");
        }
    }

    /**
     * This method is  used to update the product name
     * @param userId
     * @param categoryId
     * @param productId
     * @param productViewDto
     * @return
     */
    public String updateProductName(Long userId, Long categoryId, Long productId, ProductViewDTO productViewDto)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            if (userId.equals(product.get().getSeller().getId())) {

                Long productId1 = productRepository.findUniqueProduct(productViewDto.getBrand(), categoryId, userId, productViewDto.getName());

                if (productId1 == null) {
                    product.get().setName(productViewDto.getName());
                    productRepository.save(product.get());
                    return "Product updated successfully";
                } else {
                    throw new BadRequestException("Product name must be unique");
                }
            } else {
                throw new BadRequestException("You don't have authorization to update this product");
            }
        } else {
            throw new NotFoundException("Product not found");
        }
    }

    /**
     * This method is used to update product variation details
     * @param userId
     * @param variationId
     * @param productVariationDto
     * @return
     */
    public String updateProductVariation(Long userId, Long variationId, ProductVariationDTO productVariationDto)  {

        Optional<Product_Variation> productVariation = productVariationRepo.findById(variationId);
        if (productVariation.isPresent()) {
            if (userId.equals(productVariation.get().getProduct().getSeller().getId())) {
                if (!(productVariation.get().getProduct().isDeleted()) && productVariation.get().getProduct().isActive()) {

                    productVariation.get().setQuantity((int) productVariationDto.getQuantity());
                    productVariation.get().setActive(productVariationDto.isActive());
                    productVariation.get().setPrice(productVariationDto.getPrice());

                    productVariationRepo.save(productVariation.get());

                    return "Product variation updated successfully";
                } else {
                    throw new BadRequestException("Product may be deleted or inactive");
                }

            } else {
                throw new BadRequestException("You don't have authorization to update this product variation");
            }

        } else {
            throw new NotFoundException("Product variation not found for variation id : " + variationId);
        }

    }

    /**
     * This method is used to list all the products for admin
     * @param productId
     * @return
     */
    public List<ProductVariationGetDTO> getProductForAdmin(Long productId)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {

            List<Product_Variation> productVariations = productVariationRepo.findByProductId(productId);

            if (productVariations.size() > 0) {
                List<ProductVariationGetDTO> productVariationGetDTOS = new ArrayList<>();

                for (Product_Variation productVariation1 : productVariations) {
                    ProductVariationGetDTO productVariationGetDto = new ProductVariationGetDTO();

                    BeanUtils.copyProperties(productVariation1, productVariationGetDto);
                    productVariationGetDto.setProductId(productVariation1.getProduct().getId());
                    productVariationGetDto.setProductName(productVariation1.getProduct().getName());

                    productVariationGetDTOS.add(productVariationGetDto);
                }

                return productVariationGetDTOS;
            }

        } else {
            throw new NotFoundException("Product not found");
        }
        return null;
    }

    /**
     * This method is used to list the product for user
     * @param productId
     * @return
     */
    public List<ProductVariationGetDTO> getProductForUser(Long productId)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            if (product.get().isActive() && !(product.get().isDeleted())) {

                List<Product_Variation> productVariations = productVariationRepo.findByProductId(productId);

                if (productVariations.size() > 0) {
                    List<ProductVariationGetDTO> productVariationGetDTOS = new ArrayList<>();

                    for (Product_Variation productVariation1 : productVariations) {
                        ProductVariationGetDTO productVariationGetDto = new ProductVariationGetDTO();

                        BeanUtils.copyProperties(productVariation1, productVariationGetDto);
                        productVariationGetDto.setProductId(productVariation1.getProduct().getId());
                        productVariationGetDto.setProductName(productVariation1.getProduct().getName());

                        productVariationGetDTOS.add(productVariationGetDto);
                    }

                    return productVariationGetDTOS;
                }

            } else {
                throw new BadRequestException("Product may be deleted or inactive");
            }

        } else {
            throw new NotFoundException("Product not found");
        }
        return null;
    }

    /**
     * This method is used to list all the product by category
     * @param categoryId
     * @return
     */
    public AllProductDTO getAllProductsByCategoryId(Long categoryId)  {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {

            List<Product> products = productRepository.findAllProduct(categoryId);

            List<Product> productList = new ArrayList<>();

            for (Product product : products) {
                if (product.isActive() && !(product.isDeleted())) {
                    if (product.getProduct_variations().size() > 0) {
                        productList.add(product);
                    }
                }
            }

            AllProductDTO allProductDto = new AllProductDTO();

            allProductDto.setCategoryId(categoryId);
            allProductDto.setProductList(productList);

            return allProductDto;

        } else {
            throw new NotFoundException("Category not found for this Category Id");
        }

    }

    /**
     * This method is used to activate the product
     * @param productId
     * @return
     */
    public String activateProduct(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.get().isActive()) {
            product.get().setActive(true);
            productRepository.save(product.get());

            emailNotificationService.sendNotification("Product Activation", "Product name is : " + product.get().getName() + " and product id is : " + product.get().getId(), product.get().getSeller().getEmail());
            return "Product Activated";
        } else {
            throw new BadRequestException("Product is already activated");
        }
    }

    /**
     * This method is used to deactivate the product
     * @param productId
     * @return
     */
    public String deActivateProduct(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (product.get().isActive()) {
            product.get().setActive(false);
            productRepository.save(product.get());

            emailNotificationService.sendNotification("Product De-Activation", "Product name is : " + product.get().getName() + " and product id is : " + product.get().getId(), product.get().getSeller().getEmail());
            return "Product deActivated";
        } else {
            throw new BadRequestException("Product is already deactivated");
        }
    }

    /**
     * This method is used to get the similar products
     * @param productId
     * @return
     */
    public List<Product> getSimilarProducts(Long productId)  {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {

            List<Product> products = productRepository.findSimilarProducts(product.get().getCategory().getId(), product.get().getName());

            return products;
        } else {
            throw new NotFoundException("Product not found for product id : " + productId);
        }
    }
}