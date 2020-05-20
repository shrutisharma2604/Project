package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.CustomerDTO;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.CustomerActivateRepo;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.services.CustomerActivateService;
import com.example.EcommerceApp.services.RegisterService;
import com.example.EcommerceApp.validation.EmailValidation;
import com.example.EcommerceApp.validation.GstValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@ContextConfiguration(classes = {RegistrationController.class, RegisterService.class, CustomerActivateService.class, EmailNotificationService.class, JavaMailSenderImpl.class, JwtAccessTokenConverter.class, ResourceBundleMessageSource.class})
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userRepository")
    private UserRepository userRepository;

    @MockBean(name = "addressRepository")
    private AddressRepository addressRepository;

    @MockBean(name = "sellerRepository")
    SellerRepository sellerRepository;

    @MockBean(name = "customerActivateRepo")
    CustomerActivateRepo customerActivateRepo;

    @MockBean(name = "passwordValidation")
    PasswordValidation passwordValidation;

    @MockBean(name = "gstValidation")
    GstValidation gstValidation;

    @MockBean(name = "emailValidation")
    EmailValidation emailValidation;

    @MockBean(name = "registerService")
    RegisterService registerService;

    @InjectMocks
    RegistrationController registrationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();

    }
    @Test
    public void registerCustomer() throws Exception {
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setFirstName("Shruti");
        customerDTO.setLastName("Sharma");
        customerDTO.setContact("9870737979");
        customerDTO.setEmail("shruti.1720mca1091@kiet.edu");
        customerDTO.setPassword("Shruti@26");
        customerDTO.setConfirmPassword("Shruti@26");
        when(registerService.registerCustomer(any(CustomerDTO.class))).thenReturn("Registered Successfully");
        mockMvc.perform(post("/register/customer")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerDTO))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.confirmPassword", is("Shruti@26")))
                .andExpect(jsonPath("$.contact", is("9870737979")))
                .andExpect(jsonPath("$.email", is("shruti.1720mca1091@kiet.edu")))
                .andExpect(jsonPath("$.firstName", is("Shruti")))
                .andExpect(jsonPath("$.lastName", is("Sharma")))
                .andExpect(jsonPath("$.password", is("Shruti@26")));


        ArgumentCaptor<CustomerDTO> captor=ArgumentCaptor.forClass(CustomerDTO.class);
        verify(registerService,times(1)).registerCustomer(captor.capture());
        verifyNoMoreInteractions(registerService);

        CustomerDTO customerDTO1=captor.getValue();
        assertThat(customerDTO1.getConfirmPassword(),is("Shruti@26"));
        assertThat(customerDTO1.getContact(),is("9870737979"));
        assertThat(customerDTO1.getEmail(),is("shruti.1720mca1091@kiet.edu"));
        assertThat(customerDTO1.getFirstName(),is("Shruti"));
        assertThat(customerDTO1.getLastName(),is("Sharma"));
        assertThat(customerDTO1.getPassword(),is("Shruti@26"));
    }

}