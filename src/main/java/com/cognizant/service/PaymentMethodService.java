package com.cognizant.service;

import com.cognizant.dto.PaymentMethodDTO;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDTO> getAllPaymentMethod();
}
