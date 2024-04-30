package com.cognizant.repositories;

import com.cognizant.EmiManagementSystemApplication;
import com.cognizant.entities.EmiPayment;
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
public class EmiPaymentRepositoryTest {
    @Autowired
    private EmiPaymentRepository emiPaymentRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAllPositive() {
        EmiPayment emiPayment = new EmiPayment();
        emiPayment.setId(101);
        emiPayment.setAmount(2000.25);
        emiPayment.setPaymentDate("2024-02-29");
        emiPayment.setLateFee(150.0);
        entityManager.persist(emiPayment);
        Iterable<EmiPayment> it = emiPaymentRepository.findAll();
        assertTrue(it.iterator().hasNext());
    }

    @Test
    public void testFindAllNegative() {
        Iterable<EmiPayment> it = emiPaymentRepository.findAll();
        assertTrue(it.iterator().hasNext());
    }

    @Test
    public void testFindByIdPositive() {
        EmiPayment emiPayment = new EmiPayment();
        emiPayment.setId(101);
        emiPayment.setAmount(2000.25);
        emiPayment.setPaymentDate("2024-02-29");
        emiPayment.setLateFee(150.0);
        entityManager.persist(emiPayment);
        Optional<EmiPayment> emiPaymentOptional = emiPaymentRepository.findById(101);
        assertTrue(emiPaymentOptional.isPresent());

    }

    @Test
    public void testFindByIdNegative() {
        Optional<EmiPayment> emiPaymentOptional = emiPaymentRepository.findById(101);
        assertFalse(emiPaymentOptional.isPresent());
    }

    @Test
    public void testSavePositive() {
        EmiPayment emiPayment = new EmiPayment();
        emiPayment.setId(101);
        emiPayment.setAmount(2000.25);
        emiPayment.setPaymentDate("2024-02-29");
        emiPayment.setLateFee(150.0);
        emiPaymentRepository.save(emiPayment);
        Optional<EmiPayment> emiPaymentOptional = emiPaymentRepository.findById(101);
        assertTrue(emiPaymentOptional.isPresent());
    }


}
