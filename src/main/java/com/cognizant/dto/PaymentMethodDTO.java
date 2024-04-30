package com.cognizant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentMethodDTO {
    @Positive(message = "Payment Method Id Should be Positive")
    @NotNull
    private int id;
    @NotEmpty
    private String paymentMethod;

}
