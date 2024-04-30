package com.cognizant.repositories;

import com.cognizant.EmiManagementSystemApplication;
import com.cognizant.entities.PaymentMethod;
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
public class PaymentMethodRepositoryTest {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void TestFindAllPositive() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1001);
        paymentMethod.setPaymentMethod("Card");
        entityManager.persist(paymentMethod);
        Iterable<PaymentMethod> paymentMethodIterable = paymentMethodRepository.findAll();
        assertTrue(paymentMethodIterable.iterator().hasNext());

    }

    @Test
    public void testFindAllNegative() {
        Iterable<PaymentMethod> paymentMethodIterable = paymentMethodRepository.findAll();
        assertTrue(paymentMethodIterable.iterator().hasNext());
    }

    @Test
    public void testFindById() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1001);
        paymentMethod.setPaymentMethod("Card");
        entityManager.persist(paymentMethod);
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(1001);
        assertTrue(paymentMethodOptional.isPresent());
    }

    @Test
    public void testFindByIdNegative() {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(1001);
        assertFalse(paymentMethodOptional.isPresent());
    }

    @Test
    public void testSavePositive() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1001);
        paymentMethod.setPaymentMethod("Card");
        paymentMethodRepository.save(paymentMethod);
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(1001);
        assertTrue(paymentMethodOptional.isPresent());

    }

}
