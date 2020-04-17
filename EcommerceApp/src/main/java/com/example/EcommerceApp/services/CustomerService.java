package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.events.UserEmailFromToken;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.validation.EmailValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
    PasswordValidation passwordValidation;

  @Autowired
    EmailValidation emailValidation;

  @Autowired
    PasswordEncoder passwordEncoder;

  @Autowired
   private EmailNotificationService emailNotificationService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  UserEmailFromToken userEmailFromToken;

    public CustomerProfileDto getCustomerDetails(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
       if(customer.isPresent()){
           CustomerProfileDto customerProfileDto=new CustomerProfileDto();
           BeanUtils.copyProperties(customer.get(),customerProfileDto);
           return customerProfileDto;
       }
       else {
           throw new UserNotFoundException("User Not Found");
       }
    }
    public MappingJacksonValue getCustomerAddresses(Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isPresent()) {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer.get(), customerDto);

            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("addresses");
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("CustomerFilter", filter);

            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(customerDto);

            return mappingJacksonValue;
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }

    }

    @Transactional
    @Modifying
    public String updateCustomer(CustomerProfileDto profileDto, Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        BeanUtils.copyProperties(profileDto,customer);
        if(customer.isPresent()) {
            customer.get().setFirstName(profileDto.getFirstName());
            customer.get().setLastName(profileDto.getLastName());
            customer.get().setContact(profileDto.getContact());
            customer.get().setEmail(profileDto.getEmail());
            customerRepository.save(customer.get());
            return "Profile updated successfully";
        }
        else{
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
    public String addAddress(AddressDto addressDto,Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isPresent()){
            Address address=new Address();
            BeanUtils.copyProperties(addressDto,address);
            addressRepository.save(address);
            return "Address added";
        }
        else {
            throw new UserNotFoundException("USer Not Found");
        }
    }
    @Transactional
    public String deleteAddress(Long id, HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new UserNotFoundException("no address fount with id " + id);
        }
        addressRepository.deleteById(id);
        return "Success";
    }

    public String updateAddress(Long id,AddressDto addressDto,HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw new UserNotFoundException("no address fount with id " + id);
        }
        CustomerDto customerDto=new CustomerDto();
        Customer customer = customerRepository.findByEmail(customerDto.getEmail());
        Set<Address> addresses = customer.getAddresses();
        addresses.forEach(a -> {
            if (a.getId() == address.get().getId()) {
                a.setAddress(addressDto.getAddress());
                a.setCity(addressDto.getCity());
                a.setCountry(addressDto.getCountry());
                a.setLabel(addressDto.getLabel());
                a.setState(addressDto.getState());
                a.setZipCode(addressDto.getZipCode());
                a.setAddress(addressDto.getAddress());
            }
        });
        customerRepository.save(customer);
        return "Success";
    }
}