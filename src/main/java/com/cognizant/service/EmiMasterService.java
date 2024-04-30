package com.cognizant.service;

import com.cognizant.dto.EmiMasterDTO;


public interface EmiMasterService {
    EmiMasterDTO createEmiPlan(EmiMasterDTO emiMasterDTO);

    EmiMasterDTO viewEMIPlan(int customerId, int loanPlanId);


}
