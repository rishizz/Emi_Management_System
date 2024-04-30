package com.cognizant.dto;

import com.cognizant.entities.EmiMaster;
import com.cognizant.entities.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EmiPaymentDTO {
    @NotNull
    @Positive
    private int id;
    @NotNull
    @Positive
    private double amount;
    @NotNull
    private String paymentDate = (LocalDate.now()).toString();
    @NotNull
    @Positive
    private double lateFee;

    @NotNull
    private EmiMaster emiId;

    @NotNull
    private PaymentMethod paymentMethodId;


}
