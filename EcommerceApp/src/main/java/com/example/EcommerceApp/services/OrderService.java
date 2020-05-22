package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private OrderProductRepo orderProductRepo;

    /**
     * This method is used to place the order
     * @param orders
     * @param cid
     * @param cartId
     * @return
     */
    public String placeOrder(Orders orders,Long cid,Long cartId ){
        Optional<Cart> cart=cartRepo.findById(cartId);
        if(cart.isPresent()){
            Customer customer = cart.get().getCustomer();

            if (customer.getId().equals(cid)) {
                orders.setCustomer(customer);

                Optional<OrderAddress> orderAddress= Optional.ofNullable(orders.getOrderAddress());

                if (orderAddress.isPresent()) {

                    orders.setOrderAddress(orders.getOrderAddress());
                } else {
                    throw new NotFoundException("Address not found, Check Address Label");
                }
            } else {
                throw new NotFoundException("Cart not associated to current customer");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(orders);
            orderProduct.setProduct_variation(cart.get().getProductVariation());
            orderProduct.setQuantity(cart.get().getQuantity());

            Product_Variation product_variation = cart.get().getProductVariation();
            if (product_variation.isActive()) {

                Long pid = product_variation.getProduct().getId();

                Long vid = product_variation.getProduct_variant_id();

                // Finding the Variant from RedisDb
                Optional<ProductVariant> optionalProductVariant = productVariantRepo.findById(vid.toString());

                Optional<Product> optionalProduct = productRepository.findById(pid);
                if (optionalProduct.isPresent()) {

                    Product product = optionalProduct.get();

                    if (!product.isDeleted() && product.isActive()) {

                        if (optionalProductVariant.isPresent()) {

                            ProductVariant productVariant = optionalProductVariant.get();

                            // fetching quantity of variant from Redis instead of mysql;
                            String RedisVariantQty =  productVariant.getQuantity();

                            orderProduct.setPrice(product_variation.getPrice());
                            Long amount = orderProduct.getPrice() * cart.get().getQuantity();
                            orders.setAmountPaid((double) amount);

                            Integer qty = Integer.parseInt(RedisVariantQty);
                            Integer qty1 = cart.get().getQuantity();

                            if (qty > qty1) {

                                int remainingQty = qty-qty1;

                                //Updating the qty after order in RedisDb
                                productVariant.setQuantity(Integer.toString(remainingQty));

                                productVariantRepo.save(productVariant);
                                orderProductRepo.save(orderProduct);
                                orderRepo.save(orders);

                                cartRepo.deleteById(cartId);



                                emailNotificationService.sendNotification("Order Placed","Your Order has been successfully placed!!",customer.getEmail());

                                return "Order Placed Successfully";

                            } else {
                                throw new NotFoundException("Sorry the Requested quantity is not yet available in warehouse.");
                            }
                        }
                        else {
                            throw new NotFoundException("Invalid Variant ID");
                        }
                    }
                    else {
                        throw new NotFoundException("Sorry, The Requested product is unavailable at the moment.");
                    }
                } else {
                    throw new NotFoundException("Unable to find Product associated to selected variant");
                }
            }
            else {
                throw new NotFoundException("Requested variant is unavailable at the moment");
            }
        } else {
            throw new NotFoundException("Invalid Cart ID");
        }
    }
}
