package com.demo.spring.controller;

import com.demo.spring.model.Currency;
import com.demo.spring.model.Payment;
import com.demo.spring.model.PaymentStatus;
import com.demo.spring.model.dto.PaymentDTO;
import com.demo.spring.model.dto.PaymentMapper;
import com.demo.spring.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


class PaymentControllerTest {

    public static final String TEST_TEST_COM = "test@test.com";

    @Mock
    PaymentService paymentService;

    @InjectMocks
    PaymentController paymentController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayments() {
        // Given
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setCurrency(Currency.USD);
        paymentDTO.setAmount(BigDecimal.valueOf(300));
        paymentDTO.setCreatedDate(LocalDate.of(2022, 1, 1));

        Payment payment = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);
        Optional<Payment> optionalPayment = Optional.of(payment);

        when(paymentService.createPayment(any(Payment.class))).thenReturn(optionalPayment);

        // When
        ResponseEntity<HttpStatus> responseEntity = paymentController.createPayments(paymentDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testCreatePayments_notFound() {
        // Given
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setStatus(PaymentStatus.PAID);
        paymentDTO.setCurrency(Currency.USD);
        paymentDTO.setAmount(BigDecimal.valueOf(300));
        paymentDTO.setCreatedDate(LocalDate.of(2022, 1, 1));
        paymentDTO.setPaidDate(LocalDate.of(2022, 1, 2));

        when(paymentService.createPayment(any(Payment.class))).thenReturn(Optional.empty());

        // When
        ResponseEntity<HttpStatus> responseEntity = paymentController.createPayments(paymentDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void testUpdatePayment_nonExistingPayment_notUpdated() {
        // Given
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setStatus(PaymentStatus.PAID);
        paymentDTO.setCurrency(Currency.USD);
        paymentDTO.setAmount(BigDecimal.valueOf(300));
        paymentDTO.setCreatedDate(LocalDate.of(2022, 1, 1));
        paymentDTO.setPaidDate(LocalDate.of(2022, 1, 2));

        Payment payment = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);

        when(paymentService.updatePayment(anyLong(), any(Payment.class))).thenReturn(Optional.empty());

        // when
        ResponseEntity<HttpStatus> result = paymentController.updatePayment(1L, paymentDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(paymentService, times(1)).updatePayment(1L, payment);
    }

    @Test
    public void testUpdatePaymentExistingPayment_Updated() {
        // Given
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setCurrency(Currency.USD);
        paymentDTO.setAmount(BigDecimal.valueOf(300));
        paymentDTO.setCreatedDate(LocalDate.of(2022, 1, 1));

        Payment payment = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);
        Optional<Payment> optionalPayment = Optional.of(payment);

        when(paymentService.updatePayment(anyLong(), any(Payment.class))).thenReturn(optionalPayment);

        // when
        ResponseEntity<HttpStatus> result = paymentController.updatePayment(1L, paymentDTO);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paymentService, times(1)).updatePayment(1L, payment);
    }


    @Test
    void getPaymentsById_ReturnsPaymentDTO_WhenPaymentExists() {
        // Given
        long id = 1L;
        Payment payment = getPayment(id);
        when(paymentService.getPaymentById(anyLong())).thenReturn(Optional.of(payment));

        // When
        ResponseEntity<PaymentDTO> response = paymentController.getPaymentsById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PaymentDTO paymentDTO = response.getBody();
        assertEquals(id, paymentDTO.getId());
        assertEquals(TEST_TEST_COM, paymentDTO.getPayerEmail());
        assertEquals(PaymentStatus.PAID, paymentDTO.getStatus());
        assertEquals(Currency.USD, paymentDTO.getCurrency());
        assertEquals(BigDecimal.valueOf(100), paymentDTO.getAmount());
        assertEquals(LocalDate.of(2022, 1, 1), paymentDTO.getCreatedDate());
        assertEquals(LocalDate.of(2022, 1, 2), paymentDTO.getPaidDate());
    }



    @Test
    void getPaymentsById_ReturnsNotFound_WhenPaymentDoesNotExist() {
        // Given
        long id = 1L;
        when(paymentService.getPaymentById(anyLong())).thenReturn(Optional.empty());

        // When
        ResponseEntity<PaymentDTO> response = paymentController.getPaymentsById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, ((ResponseEntity<?>) response).getStatusCode());
    }


    @Test
    void deletePayment_ReturnsNoContent() {
        long paymentId = 1L;

        ResponseEntity<HttpStatus> response = paymentController.deletePayment(paymentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void testGetAllPayments_listOfPayments() {

        Payment payment1 = getPayment(1L);
        Payment payment2 = getPayment(2L);
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentService.getAllPayments()).thenReturn(payments);

        ResponseEntity<List<PaymentDTO>> response = paymentController.getAllPayments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(payment1.getId(), response.getBody().get(0).getId());
        assertEquals(payment1.getAmount(), response.getBody().get(0).getAmount());
        assertEquals(payment1.getCurrency(), response.getBody().get(0).getCurrency());
        assertEquals(payment2.getId(), response.getBody().get(1).getId());
        assertEquals(payment2.getAmount(), response.getBody().get(1).getAmount());
        assertEquals(payment2.getCurrency(), response.getBody().get(1).getCurrency());

        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testGetAllPayments_noPayments() {

        List<Payment> payments = new ArrayList<>();

        when(paymentService.getAllPayments()).thenReturn(payments);

        ResponseEntity<List<PaymentDTO>> response = paymentController.getAllPayments();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(paymentService, times(1)).getAllPayments();
    }

    private static Payment getPayment(long id) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setPayerEmail(TEST_TEST_COM);
        payment.setStatus(PaymentStatus.PAID);
        payment.setCurrency(Currency.USD);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setCreatedDate(LocalDate.of(2022, 1, 1));
        payment.setPaidDate(LocalDate.of(2022, 1, 2));
        return payment;
    }

}