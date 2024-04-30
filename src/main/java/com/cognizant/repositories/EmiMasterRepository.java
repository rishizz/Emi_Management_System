package com.cognizant.repositories;

import com.cognizant.entities.EmiMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmiMasterRepository extends CrudRepository<EmiMaster, Integer> {
    Optional<EmiMaster> findByCustomerIdAndLoanPlanId(int customerId, int loanPlanId);
    List<EmiMaster> findByCustomerId(int customerId);
}
