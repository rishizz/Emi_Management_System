package com.cognizant.repositories;

import com.cognizant.entities.EmiPayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmiPaymentRepository extends CrudRepository<EmiPayment, Integer> {
    @Query("SELECT e FROM EmiPayment e JOIN e.emiId em WHERE em.customerId = :customerId AND em.loanPlanId = :loanPlanId")
    List<EmiPayment> findByCustomerIdAndLoanPlanId(@Param("customerId") int customerId, @Param("loanPlanId") int loanPlanId);
}
