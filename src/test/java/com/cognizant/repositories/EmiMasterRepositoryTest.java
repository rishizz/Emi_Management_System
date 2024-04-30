package com.cognizant.repositories;


import com.cognizant.EmiManagementSystemApplication;
import com.cognizant.entities.EmiMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = EmiManagementSystemApplication.class)
public class EmiMasterRepositoryTest {
    @Autowired
    private EmiMasterRepository emiMasterRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAllPositive() {
        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(100);
        emiMaster.setLoanPlanId(234);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);
        emiMaster.setCustomerName("Rishikesh");
        emiMaster.setCustomerPhone("9876543210");
        emiMaster.setCustomerAddress("21 street avenue, london");
        emiMaster.setCustomerPan("1243658709");
        emiMaster.setEmiStatus("OnGoing");


        entityManager.persist(emiMaster);
        Iterable<EmiMaster> it = emiMasterRepository.findAll();
        assertTrue(it.iterator().hasNext());

    }

    @Test
    public void testFindAllNegative() {
        Iterable<EmiMaster> it = emiMasterRepository.findAll();
        assertTrue(it.iterator().hasNext());
    }

    @Test
    public void testFindByIdPositive() {
        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(100);
        emiMaster.setLoanPlanId(234);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);
        emiMaster.setCustomerName("Rishikesh");
        emiMaster.setCustomerPhone("9876543210");
        emiMaster.setCustomerAddress("21 street avenue, london");
        emiMaster.setCustomerPan("1243658709");
        emiMaster.setEmiStatus("OnGoing");
        entityManager.persist(emiMaster);
        Optional<EmiMaster> emiMasterOptional = emiMasterRepository.findById(101);
        assertTrue(emiMasterOptional.isPresent());
    }

    @Test
    public void testFindByIdNegative() {
        Optional<EmiMaster> emiMasterOptional = emiMasterRepository.findById(101);
        assertFalse(emiMasterOptional.isPresent());
    }

    @Test
    public void testSavePositive() {
        EmiMaster emiMaster = new EmiMaster();
        emiMaster.setId(101);
        emiMaster.setCustomerId(100);
        emiMaster.setLoanPlanId(234);
        emiMaster.setEmiAmount(2000.25);
        emiMaster.setNumberOfEmi(24);
        emiMaster.setCustomerName("Rishikesh");
        emiMaster.setCustomerPhone("9876543210");
        emiMaster.setCustomerAddress("21 street avenue, london");
        emiMaster.setCustomerPan("1243658709");
        emiMaster.setEmiStatus("OnGoing");
        emiMasterRepository.save(emiMaster);
        Optional<EmiMaster> emiMasterOptional = emiMasterRepository.findById(101);
        assertTrue(emiMasterOptional.isPresent());

    }
}
