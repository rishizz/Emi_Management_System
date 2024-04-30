package com.cognizant.service.impl;

import com.cognizant.dto.EmiMasterDTO;
import com.cognizant.entities.EmiMaster;
import com.cognizant.exceptions.EmiPlanLimitExceededException;
import com.cognizant.exceptions.LoanPlanIdNotDistinctException;
import com.cognizant.repositories.EmiMasterRepository;
import com.cognizant.service.EmiMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmiMasterServiceImpl implements EmiMasterService {

    @Autowired
    private EmiMasterRepository emiMasterRepository;

    @Override
    public EmiMasterDTO createEmiPlan(EmiMasterDTO emiMasterDTO){
        List<EmiMaster> existingPlans = emiMasterRepository.findByCustomerId(emiMasterDTO.getCustomerId());
        if (existingPlans.size() >= 2) {
            throw new EmiPlanLimitExceededException("Cannot create more than two EmiPlans for a customer");
        }

        for (EmiMaster plan : existingPlans) {
            if (plan.getLoanPlanId() == emiMasterDTO.getLoanPlanId()) {
                throw new LoanPlanIdNotDistinctException("loanPlanId must be distinct for a customer");
            }
        }

        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(emiMasterDTO.getId());
        emiMaster.setCustomerId(emiMasterDTO.getCustomerId());
        emiMaster.setLoanPlanId(emiMasterDTO.getLoanPlanId());
        emiMaster.setEmiAmount(emiMasterDTO.getEmiAmount());
        emiMaster.setEmiStart(emiMasterDTO.getEmiStart());
        emiMaster.setNumberOfEmi(emiMasterDTO.getNumberOfEmi());
        emiMaster.setCustomerName(emiMasterDTO.getCustomerName());
        emiMaster.setCustomerPhone(emiMasterDTO.getCustomerPhone());
        emiMaster.setCustomerAddress(emiMasterDTO.getCustomerAddress());
        emiMaster.setCustomerPan(emiMasterDTO.getCustomerPan());
        emiMaster.setEmiStatus(emiMasterDTO.getEmiStatus());
        emiMasterRepository.save(emiMaster);
        return emiMasterDTO;
    }

    @Override
    public EmiMasterDTO viewEMIPlan(int customerId, int loanPlanId) {
        Optional<EmiMaster> emiMaster = emiMasterRepository.findByCustomerIdAndLoanPlanId(customerId, loanPlanId);
        return emiMaster.map(this::convertToDTO).orElse(null);
    }
    private EmiMasterDTO convertToDTO(EmiMaster emiMaster) {
        return EmiMasterDTO.builder()
                .id(emiMaster.getId())
                .customerId(emiMaster.getCustomerId())
                .loanPlanId(emiMaster.getLoanPlanId())
                .emiAmount(emiMaster.getEmiAmount())
                .emiStart(emiMaster.getEmiStart())
                .numberOfEmi(emiMaster.getNumberOfEmi())
                .customerName(emiMaster.getCustomerName())
                .customerPhone(emiMaster.getCustomerPhone())
                .customerAddress(emiMaster.getCustomerAddress())
                .customerPan(emiMaster.getCustomerPan())
                .emiStatus(emiMaster.getEmiStatus())
                .build();
    }

}
