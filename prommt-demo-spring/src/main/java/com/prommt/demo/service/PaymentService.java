package com.prommt.demo.service;

import com.prommt.demo.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Optional<Payment> updatePayment(Long id, Payment payment);

    Optional<Payment> createPayment(Payment payment);

    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(Long id);

    void deletePayment(Long id);

}
