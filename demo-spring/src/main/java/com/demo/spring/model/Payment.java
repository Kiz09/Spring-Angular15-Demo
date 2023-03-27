package com.demo.spring.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="createdDate")
    public LocalDate createdDate;

    @Column(name="payerEmail")
    public String payerEmail;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public PaymentStatus status;

    @Column(name="currency")
    @Enumerated(EnumType.STRING)
    public Currency currency;

    @Column(name="amount")
    public BigDecimal amount;

    @Column(name="paidDate")
    public LocalDate paidDate;

}
