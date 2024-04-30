package com.cognizant.controller;

import com.cognizant.dto.PaymentMethodDTO;
import com.cognizant.service.PaymentMethodService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/paymentmethods")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:4200/")
public class PaymentMethodController {

    private PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getPaymentMethods() {
        List<PaymentMethodDTO> paymentMethodDTOList = paymentMethodService.getAllPaymentMethod();
        if (paymentMethodDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Payment Method" + paymentMethodDTOList);
        return new ResponseEntity<>(paymentMethodDTOList, HttpStatus.OK);
    }

}
