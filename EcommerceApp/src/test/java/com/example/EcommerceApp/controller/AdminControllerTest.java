package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.repositories.CustomerActivateRepo;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.services.AdminService;
import com.example.EcommerceApp.services.CustomerActivateService;
import com.example.EcommerceApp.validation.EmailValidation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {AdminService.class, EmailNotificationService.class, CustomerActivateService.class})
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userRepository")
    private UserRepository userRepository;

    @MockBean(name = "customerRepository")
    private CustomerRepository customerRepository;

    @MockBean(name = "sellerRepository")
    SellerRepository sellerRepository;

    @MockBean(name = "messageSource")
    private MessageSource messageSource;

    @MockBean(name = "javaMailSender")
    private JavaMailSenderImpl javaMailSender;

    @MockBean(name = "tokenStore")
    private TokenStore tokenStore;

    @MockBean(name = "emailValidation")
    EmailValidation emailValidation;

    @MockBean(name = "customerActivateRepo")
    CustomerActivateRepo customerActivateRepo;

    @Test
    public void getCustomers() throws Exception{
        Mockito.when(javaMailSender.getHost()).thenReturn("qwertyuiop");
        Mockito.when(javaMailSender.getPort()).thenReturn(10000);
        Mockito.when(javaMailSender.getUsername()).thenReturn("qwertyui");
        Mockito.when(javaMailSender.getPassword()).thenReturn("qwertyui");
        mockMvc.perform( MockMvcRequestBuilders
                .get("/admin/home/customers")
                .with(SecurityMockMvcRequestPostProcessors.user("shrutisharma260419@gmail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("page","0")
                .param("size","10")
                .param("SortBy","id")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$./admin/home/customers").exists());
    }
}