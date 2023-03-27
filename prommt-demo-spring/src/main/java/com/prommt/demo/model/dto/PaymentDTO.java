package com.prommt.demo.model.dto;

import com.prommt.demo.model.Currency;
import com.prommt.demo.model.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {

    private Long id;
    private LocalDate createdDate;
    private String payerEmail;
    private PaymentStatus status;
    private Currency currency;
    private BigDecimal amount;
    private LocalDate paidDate;

}
