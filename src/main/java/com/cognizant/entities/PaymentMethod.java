package com.cognizant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Payment_Methods")
@Data
public class PaymentMethod {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Payment_Method")
    private String paymentMethod;

}
