package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private ProductVariantRepo productVariantRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    Logger logger = LoggerFactory.getLogger(CartService.class);

    /**
     * This method is used to add the product in the cart
     * @param cart
     * @param cid
     * @param vid
     * @return
     */
    public String addToCart(Cart cart, Long cid, Long vid) {

        Optional<User> customer = userRepository.findById(cid);
        if (customer.isPresent()) {
            User user = new User();
            user = customer.get();

            Customer customer1 = new Customer();
            customer1 = (Customer) user;

            cart.setCustomer(customer1);

            // variant from Mysql
            Optional<Product_Variation> product_variation = productVariationRepo.findById(vid);

            if (product_variation.isPresent()) {
                Product_Variation productVariation = new Product_Variation();
                productVariation = product_variation.get();

                if (productVariation.isActive()) {

                    Long pid = productVariation.getProduct().getId();

                    // Finding the Variant from RedisDb
                    Optional<ProductVariant> optionalProductVariant = productVariantRepository.findById(vid.toString());

                    Optional<Product> optionalProduct = productRepository.findById(pid);
                    if (optionalProduct.isPresent()) {

                        Product product = optionalProduct.get();

                        if(!product.isDeleted() && product.isActive()) {

                            if (optionalProductVariant.isPresent()) {

                                ProductVariant productVariant = optionalProductVariant.get();

                                // fetching quantity of variant from Redis instead of mysql;
                                String RedisVariantQty = productVariant.getQuantity();

                                Integer qty = Integer.parseInt(RedisVariantQty);

                                Integer cartQuantity = cart.getQuantity();
                                if (cartQuantity < qty) {
                                    cart.setProductVariation(productVariation);
                                    cartRepo.save(cart);

                                    return "Item Added to cart Successfully ";

                                } else {
                                    throw new NotFoundException("Ordered Quantity is greater than available stock in Warehouse.");
                                }
                            }  else {
                                throw new NotFoundException("Invalid Variant ID");
                            }
                        }
                        else {
                            throw new NotFoundException("Sorry, The Requested product is unavailable at the moment.");
                        }
                    }
                    else {
                        throw new NotFoundException("Unable to find Product associated to selected variant");
                    }
                }
                else
                {
                    throw new NotFoundException("Requested variant is unavailable at the moment");
                }
            }
            else {
                throw new NotFoundException("Invalid Product Variation ID");
            }
        }
        else {
            throw new NotFoundException("Invalid customer ID");
        }
    }

    /**
     * This product is used to add the product in the wishlist
     * @param cid
     * @param vid
     * @return
     */
    public String addToWishlist (Long cid, Long vid) {

        Optional<User> customer = userRepository.findById(cid);
        if (customer.isPresent()) {
            User user = new User();
            user = customer.get();

            Customer customer1 = new Customer();
            customer1 = (Customer) user;

            Optional<Product_Variation> product_variation = productVariationRepo.findById(vid);

            if (product_variation.isPresent()) {
                Product_Variation productVariation = new Product_Variation();
                productVariation = product_variation.get();

                Long pid = productVariation.getProduct().getId();

                Optional<Product> optionalProduct = productRepository.findById(pid);
                if (optionalProduct.isPresent()) {

                    Product product = optionalProduct.get();

                    if (!product.isDeleted()) {

                        Wishlist wishlist = new Wishlist();
                        wishlist.setCustomer(customer1);
                        wishlist.setProduct_variation(productVariation);
                        wishlistRepository.save(wishlist);

                        logger.info("Product Added to Wishlist by Customer");

                        return "Item Added to wishlist successfully ";

                    } else {
                        throw new NotFoundException("Requested Product is no longer available");
                    }

                } else {
                    throw new NotFoundException("Unable to find Product associated to selected variant");
                }

            } else {
                throw new NotFoundException("Invalid Product Variation ID");
            }
        } else {
            throw new UserNotFoundException("Invalid customer ID");
        }
    }

}
