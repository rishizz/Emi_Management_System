package com.cognizant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Emi_Payments")
public class EmiPayment {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Amount")
    private double amount;
    @Column(name = "Payment_Date")
    private String paymentDate;
    @Column(name = "Late_Fee")
    private double lateFee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Payment_Method_Id", referencedColumnName = "Id")
    private PaymentMethod paymentMethodId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMIs_Id", referencedColumnName = "Id")
    private EmiMaster emiId;

}
