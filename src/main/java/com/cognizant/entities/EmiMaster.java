package com.cognizant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "EMIs_Master")
public class EmiMaster {
    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "Loan_Plan_Id")
    private int loanPlanId;
    @Column(name = "EMI_Amount")
    private double emiAmount;
    @Column(name = "EMI_Start")
    private String emiStart;
    @Column(name = "Number_Of_EMIs")
    private int numberOfEmi;
    @Column(name = "Customer_Name")
    private String customerName;
    @Column(name = "Customer_Phone")
    private String customerPhone;
    @Column(name = "Customer_Address")
    private String customerAddress;
    @Column(name = "Customer_PAN")
    private String customerPan;
    @Column(name = "EMI_Status")
    private String emiStatus;



}
