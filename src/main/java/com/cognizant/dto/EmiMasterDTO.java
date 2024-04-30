package com.cognizant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmiMasterDTO {
    @Positive(message = "Id must be a positive number")
    @NotNull
    private int id;
    @Positive(message = "Customer Id must be positive")
    @NotNull
    private int customerId;
    @NotNull
    @Positive
    private int loanPlanId;
    @NotNull
    private double emiAmount;
    @NotEmpty
    private String emiStart;
    @NotNull
    private int numberOfEmi;
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String customerPhone;
    @NotEmpty
    private String customerAddress;
    @NotEmpty
    private String customerPan;
    @NotEmpty
    private String emiStatus = "OnGoing";


}
