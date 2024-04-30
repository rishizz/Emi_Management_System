package com.cognizant.controller;

import com.cognizant.dto.PaymentMethodDTO;
import com.cognizant.service.PaymentMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PaymentMethodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentMethodService paymentMethodService;

    @InjectMocks
    private PaymentMethodController paymentMethodController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentMethodController).build();
    }

    @Test
    void testGetPaymentMethodsPositive() throws Exception {
        List<PaymentMethodDTO> responseList = new ArrayList<>();
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(1);
        paymentMethodDTO.setPaymentMethod("Test Payment Method");
        responseList.add(paymentMethodDTO);

        when(paymentMethodService.getAllPaymentMethod()).thenReturn(responseList);

        mockMvc.perform(get("/api/paymentmethods"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentMethodsNegative() throws Exception {
        List<PaymentMethodDTO> responseList = new ArrayList<>();

        when(paymentMethodService.getAllPaymentMethod()).thenReturn(responseList);

        mockMvc.perform(get("/api/paymentmethods"))
                .andExpect(status().isBadRequest());
    }
}
