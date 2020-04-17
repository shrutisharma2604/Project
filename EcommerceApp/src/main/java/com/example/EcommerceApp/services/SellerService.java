package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.events.UserEmailFromToken;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserEmailFromToken userEmailFromToken;

    public SellerProfileDto getSellerDetails(Long id){
        Optional<Seller> seller = sellerRepository.findById(id);

        if(seller.isPresent()){
            SellerProfileDto sellerProfileDto=new SellerProfileDto();
            BeanUtils.copyProperties(seller.get(),sellerProfileDto);
            return sellerProfileDto;
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }
    }
    @Transactional
    @Modifying
    public String updateSeller(SellerProfileDto sellerProfileDto, Long id){
        Optional<Seller> seller = sellerRepository.findById(id);
        if(seller.isPresent()) {
            seller.get().setFirstName(sellerProfileDto.getFirstName());
            seller.get().setLastName(sellerProfileDto.getLastName());
            seller.get().setCompanyContact(sellerProfileDto.getCompanyContact());
            seller.get().setGST(sellerProfileDto.getGST());

            sellerRepository.save(seller.get());
            return "Profile update Successfully";
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String updatePassword(Long id, String oldPass, String newPass, String confirmPass, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            if (passwordEncoder.matches(oldPass, user.get().getPassword())) {

                if (newPass.equals(confirmPass)) {
                    user.get().setPassword(passwordEncoder.encode(newPass));
                    userRepository.save(user.get());

                    String email = user.get().getEmail();
                    emailNotificationService.sendNotification("Password Changed", "Your password has changed", email);

                    return "Password successfully changed";
                } else {
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
        return "Success";
    }

    public String updateAddress(Long id, SellerAddressDto addressDto, HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new UserNotFoundException("no address fount with id " + id);
        }
        Seller seller = sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        Set<Address> addresses = seller.getAddresses();
        addresses.forEach(a->{
            if (a.getId() == address.get().getId()) {
                a.setAddress(addressDto.getAddress());
                a.setCity(addressDto.getCity());
                a.setCountry(addressDto.getCountry());
                a.setState(addressDto.getState());
                a.setZipCode(addressDto.getZipCode());
                a.setAddress(addressDto.getAddress());
            }
        });
        sellerRepository.save(seller);
        return "Success";
    }
}
