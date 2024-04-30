package com.cognizant.service;

import com.cognizant.dto.PaymentMethodDTO;
import com.cognizant.entities.PaymentMethod;
import com.cognizant.repositories.PaymentMethodRepository;
import com.cognizant.service.impl.PaymentMethodServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentMethodServiceImplTest {
    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    public void testGetAllPaymentMethod(){
        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setId(1);
        paymentMethod1.setPaymentMethod("Card");

        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setId(2);
        paymentMethod2.setPaymentMethod("NetBanking");

        PaymentMethod paymentMethod3 = new PaymentMethod();
        paymentMethod3.setId(3);
        paymentMethod3.setPaymentMethod("UPI");

        when(paymentMethodRepository.findAll()).thenReturn(Arrays.asList(paymentMethod1, paymentMethod2,paymentMethod3));

        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodService.getAllPaymentMethod();

        assertEquals(3, paymentMethodDTOs.size());
        assertEquals(paymentMethod1.getId(), paymentMethodDTOs.get(0).getId());
        assertEquals(paymentMethod1.getPaymentMethod(), paymentMethodDTOs.get(0).getPaymentMethod());
        assertEquals(paymentMethod2.getId(), paymentMethodDTOs.get(1).getId());
        assertEquals(paymentMethod2.getPaymentMethod(), paymentMethodDTOs.get(1).getPaymentMethod());
        assertEquals(paymentMethod3.getId(), paymentMethodDTOs.get(2).getId());
        assertEquals(paymentMethod3.getPaymentMethod(), paymentMethodDTOs.get(2).getPaymentMethod());

    }
    @Test
    public void testGetAllPaymentMethodEmpty() {
        when(paymentMethodRepository.findAll()).thenReturn(List.of());

        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodService.getAllPaymentMethod();

        assertTrue(paymentMethodDTOs.isEmpty());
    }

}
