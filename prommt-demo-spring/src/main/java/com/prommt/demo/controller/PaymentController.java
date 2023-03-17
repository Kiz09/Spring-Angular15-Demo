package com.prommt.demo.controller;

import com.prommt.demo.model.Payment;
import com.prommt.demo.model.dto.PaymentDTO;
import com.prommt.demo.model.dto.PaymentMapper;
import com.prommt.demo.repository.PaymentRepository;
import com.prommt.demo.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PaymentController {

    PaymentRepository paymentRepository;

    PaymentService paymentService;

    public PaymentController(PaymentRepository paymentRepository, PaymentService paymentService) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentsById(@PathVariable("id") long id){

        Optional<Payment> payment = paymentService.getPaymentById(id);

        if(payment.isPresent()){
            return ResponseEntity.ok(PaymentMapper.INSTANCE.paymentToPaymentDTO(payment.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
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

        paymentRepository.deleteById(id);
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
