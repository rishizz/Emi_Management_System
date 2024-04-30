package com.cognizant.service.impl;

import com.cognizant.dto.EmiPaymentDTO;
import com.cognizant.entities.EmiMaster;
import com.cognizant.entities.EmiPayment;
import com.cognizant.entities.PaymentMethod;
import com.cognizant.exceptions.LoanPaymentCompletedException;
import com.cognizant.repositories.EmiMasterRepository;
import com.cognizant.repositories.EmiPaymentRepository;
import com.cognizant.repositories.PaymentMethodRepository;
import com.cognizant.service.EmiPaymentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmiPaymentServiceImpl implements EmiPaymentService {
    @Autowired
    private EmiPaymentRepository emiPaymentRepository;
    @Autowired
    private EmiMasterRepository emiMasterRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EmiPaymentDTO payEMI(int customerId, int loanPlanId, EmiPaymentDTO emiPaymentDTO,int paymentMethodId) throws LoanPaymentCompletedException {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(paymentMethodId);
        Optional<EmiMaster> emiMaster = emiMasterRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId);

        if (emiMaster.isEmpty()) {
            throw new EntityNotFoundException("No EMI plan found for the given customer ID and loan plan ID");
        }
        if(paymentMethod.isEmpty()){
            throw new EntityNotFoundException("No payment method for given payment method Id");
        }
        if (getTotalNumberOfPaidEmi(customerId, loanPlanId) >= getTotalNumberOfEMI(customerId, loanPlanId)) {
            throw new LoanPaymentCompletedException("Loan payment completed exception");
        }

        if (checkPaymentDate(emiPaymentDTO.getPaymentDate()) > 5) {
            double totalFine = (checkPaymentDate(emiPaymentDTO.getPaymentDate()) - 5) * 0.0025;
            double lateFee = emiMaster.get().getEmiAmount() * totalFine;
            emiPaymentDTO.setLateFee(lateFee);
        }


        EmiPayment emiPayment = new EmiPayment();
        emiPayment.setId(emiPaymentDTO.getId());
        emiPayment.setAmount(emiMaster.get().getEmiAmount());
        emiPayment.setPaymentDate(emiPaymentDTO.getPaymentDate());
        emiPayment.setLateFee(emiPaymentDTO.getLateFee());
        emiPayment.setPaymentMethodId(paymentMethod.get());
        emiPayment.setEmiId(emiMaster.get());
        emiPaymentRepository.save(emiPayment);

        emiPaymentDTO.setAmount(emiMaster.get().getEmiAmount());
        emiPaymentDTO.setLateFee(emiPayment.getLateFee());
        emiPaymentDTO.setPaymentMethodId(paymentMethod.get());
        emiPaymentDTO.setEmiId(emiMaster.get());



        return emiPaymentDTO;
    }

    public int getTotalNumberOfEMI(int customerId, int loanPlanId) {
        Optional<EmiMaster> emiMaster = emiMasterRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId);
        if (emiMaster.isPresent()) {
            return emiMaster.get().getNumberOfEmi();
        } else {
            throw new EntityNotFoundException("No EMI plan found for the given customer ID and loan Id");
        }
    }

    public int checkPaymentDate(String date) {
        LocalDate paymentDateObj = LocalDate.parse(date);

        return paymentDateObj.getDayOfMonth();

    }

    public int getTotalNumberOfPaidEmi(int customerId, int loanPlanId) {
        Optional<EmiMaster> emiMaster = emiMasterRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId);
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM EMI_PAYMENTS WHERE EMIS_ID = :emiMasterId");
        query.setParameter("emiMasterId", emiMaster.get().getId());
        return ((Number) query.getSingleResult()).intValue();
    }



    @Override
    public List<EmiPaymentDTO> viewEMIPayments(int customerId, int loanPlanId) {
        List<EmiPayment> emiPayments = emiPaymentRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId);
        if (emiPayments.isEmpty()) {
            throw new RuntimeException("No EMI payments found for the given customer ID and loan plan ID");
        }
          return emiPayments.stream().map(this::convertToDTO).toList();
    }
    public EmiPaymentDTO convertToDTO(EmiPayment emiPayment) {
        EmiPaymentDTO emiPaymentDTO = new EmiPaymentDTO();
        emiPaymentDTO.setId(emiPayment.getId());
        emiPaymentDTO.setAmount(emiPayment.getAmount());
        emiPaymentDTO.setPaymentDate(emiPayment.getPaymentDate());
        emiPaymentDTO.setLateFee(emiPayment.getLateFee());
        emiPaymentDTO.setEmiId(emiPayment.getEmiId());
        emiPaymentDTO.setPaymentMethodId(emiPayment.getPaymentMethodId());
        return emiPaymentDTO;
    }
}
