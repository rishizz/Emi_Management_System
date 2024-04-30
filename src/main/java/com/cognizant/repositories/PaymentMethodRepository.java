package com.cognizant.repositories;

import com.cognizant.entities.PaymentMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Integer> {
    List <PaymentMethod> findAll();
}
