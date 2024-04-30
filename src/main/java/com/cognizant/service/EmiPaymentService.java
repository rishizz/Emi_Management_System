package com.cognizant.service;

import com.cognizant.dto.EmiPaymentDTO;
import com.cognizant.exceptions.LoanPaymentCompletedException;

import java.util.List;

public interface EmiPaymentService {
    EmiPaymentDTO payEMI(int customerId, int loanPlanId, EmiPaymentDTO emiPaymentDTO,int paymentMethodId) throws LoanPaymentCompletedException;

    List<EmiPaymentDTO> viewEMIPayments(int customerId, int loanPlanId);


}
