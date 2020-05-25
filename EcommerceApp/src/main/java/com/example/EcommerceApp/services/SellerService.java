package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.AddressDTO;
import com.example.EcommerceApp.dto.SellerProfileDTO;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.BadRequestException;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

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

    Logger logger = LoggerFactory.getLogger(SellerService.class);

    /**
     * This method is used to get the details of particular seller
     * @param id
     * @return
     */
    public SellerProfileDTO getSellerDetails(Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);

        if (seller.isPresent()) {
            SellerProfileDTO sellerProfileDto = new SellerProfileDTO();
            BeanUtils.copyProperties(seller.get(), sellerProfileDto);
            return sellerProfileDto;
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    /**
     * This method is used to update the details of seller
     * @param sellerProfileDto
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    public String updateSeller(SellerProfileDTO sellerProfileDto, Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
            if(sellerProfileDto.getFirstName()!=null) {
                seller.get().setFirstName(sellerProfileDto.getFirstName());
            }
            if(sellerProfileDto.getLastName()!=null) {
                seller.get().setLastName(sellerProfileDto.getLastName());
            }
            if(sellerProfileDto.getCompanyContact()!=null) {
                if (sellerProfileDto.getCompanyContact().length() != 10) {
                    throw new BadRequestException("contact is invalid");
                }
                seller.get().setCompanyContact(sellerProfileDto.getCompanyContact());
            }
            sellerRepository.save(seller.get());
            return "Profile update Successfully";
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * This method is used to update the password of particular seller
     * @param id
     * @param oldPass
     * @param newPass
     * @param confirmPass
     * @param httpServletResponse
     * @return
     */
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

    /**
     * This method is used to update the address for particular seller
     * @param addressDto
     * @param id
     * @param userId
     * @return
     */
    @Transactional
    @Modifying
    public String updateAddress(AddressDTO addressDto, Long id,Long userId) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            Address address1 = address.get();
            Long sid = address1.getUser().getId();
            if (sid.equals(userId)) {
                if(addressDto.getAddress()!=null) {
                    address.get().setAddress(addressDto.getAddress());
                }
                if(addressDto.getCity()!=null) {
                    address.get().setCity(addressDto.getCity());
                }
                if(addressDto.getCountry()!=null) {
                    address.get().setCountry(addressDto.getCountry());
                }
                if(addressDto.getLabel()!=null) {
                    address.get().setLabel(addressDto.getLabel());
                }
                if (addressDto.getState()!=null) {
                    address.get().setState(addressDto.getState());
                }
                if(addressDto.getZipCode()!=null) {
                    address.get().setZipCode(addressDto.getZipCode());
                }
                addressRepository.save(address.get());
                return "Address Updated Successfully";
            } else {
                throw new NotFoundException("Address not associate to current seller");
            }
        } else {
            throw new NotFoundException("Invalid address id");
        }
    }
}