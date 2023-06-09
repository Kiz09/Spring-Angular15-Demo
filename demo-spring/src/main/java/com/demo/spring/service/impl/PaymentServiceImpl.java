package com.demo.spring.service.impl;

import com.demo.spring.model.Payment;
import com.demo.spring.model.PaymentStatus;
import com.demo.spring.repository.PaymentRepository;
import com.demo.spring.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
    public Optional<Payment> getPaymentById(Long id){
        return paymentRepository.findById(id);
    }
    public void deletePayment(Long id){
        paymentRepository.deleteById(id);
    }

    public Optional<Payment> updatePayment(Long id, Payment payment){
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if(paymentOptional.isPresent()){
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidDate(LocalDate.now());
            return Optional.of(paymentRepository.save(payment));
        }
        return Optional.empty();
    }


    public Optional<Payment> createPayment(Payment payment){
        if(paymentRepository.findById(payment.getId()).isPresent()){
            return Optional.empty();
        }

        payment.setCreatedDate(LocalDate.now());
        payment.setStatus(PaymentStatus.CREATED);

        return Optional.of(paymentRepository.save(payment));
    }

}
