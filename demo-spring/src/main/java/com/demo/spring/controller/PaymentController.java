package com.demo.spring.controller;

import com.demo.spring.model.Payment;
import com.demo.spring.model.dto.PaymentDTO;
import com.demo.spring.model.dto.PaymentMapper;
import com.demo.spring.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentServiceImpl) {
        this.paymentService = paymentServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentsById(@PathVariable("id") long id){

        Optional<Payment> payment = paymentService.getPaymentById(id);

        return payment.map(value -> ResponseEntity.ok(PaymentMapper.INSTANCE.paymentToPaymentDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createPayments(@RequestBody PaymentDTO paymentDTO){

        Payment payment = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);

        if(paymentService.createPayment(payment).isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable("id") long id){

        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePayment(@PathVariable long id, @RequestBody PaymentDTO paymentDTO){

        Payment paymentToUpdate = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);
        Optional<Payment> payment = paymentService.updatePayment(id, paymentToUpdate);

        if(payment.isPresent()){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDTO>> getAllPayments(){

        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        List<Payment> payments = paymentService.getAllPayments();

        if(payments.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        for (Payment p: payments) {
            paymentDTOS.add(PaymentMapper.INSTANCE.paymentToPaymentDTO(p));
        }

        return ResponseEntity.ok(paymentDTOS);

    }

}
