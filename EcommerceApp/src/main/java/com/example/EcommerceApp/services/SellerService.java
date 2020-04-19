package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.AddressDTO;
import com.example.EcommerceApp.dto.SellerProfileDTO;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
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

    public SellerProfileDTO getSellerDetails(Long id){
        Optional<Seller> seller = sellerRepository.findById(id);

        if(seller.isPresent()){
            SellerProfileDTO sellerProfileDto=new SellerProfileDTO();
            BeanUtils.copyProperties(seller.get(),sellerProfileDto);
            return sellerProfileDto;
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }
    }
    @Transactional
    @Modifying
    public String updateSeller(SellerProfileDTO sellerProfileDto, Long id){
        Optional<Seller> seller = sellerRepository.findById(id);
        if(seller.isPresent()) {
            seller.get().setFirstName(sellerProfileDto.getFirstName());
            seller.get().setLastName(sellerProfileDto.getLastName());
            seller.get().setCompanyContact(sellerProfileDto.getCompanyContact());
            seller.get().setGST(sellerProfileDto.getGST());
            seller.get().setImage(sellerProfileDto.getImage());

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

    @Transactional
    @Modifying
    public String updateAddress(AddressDTO addressDto, Long addressId, Long userId) {

        Optional<Seller> seller = sellerRepository.findById(userId);

        if (seller.isPresent()) {
            Optional<Address> addressExist = addressRepository.findById(addressId);
            StringBuilder sb = new StringBuilder();

            if (addressExist.isPresent()) {
                Address address = new Address();

                System.out.println("Status deleted : ");
                BeanUtils.copyProperties(addressDto, address);

                address.setDeleted(true);

                addressRepository.save(address);

                sb.append("Address updated");
            } else {
                throw new NotFoundException("Address not found");
            }
        } else {
            throw new NotFoundException("User not found");
        }
        return "Success";
    }
}
