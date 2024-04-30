package com.cognizant.service.impl;

import com.cognizant.dto.PaymentMethodDTO;
import com.cognizant.entities.PaymentMethod;
import com.cognizant.repositories.PaymentMethodRepository;
import com.cognizant.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethod() {
        List<PaymentMethod> paymentMethodsList =  paymentMethodRepository.findAll();
        return paymentMethodsList.stream().map(this::convertToDTO).toList();

    }

    private PaymentMethodDTO convertToDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(paymentMethod.getId());
        paymentMethodDTO.setPaymentMethod(paymentMethod.getPaymentMethod());
        return paymentMethodDTO;
    }

}
