package com.demo.spring.service;

import com.demo.spring.model.Currency;
import com.demo.spring.model.Payment;
import com.demo.spring.model.PaymentStatus;
import com.demo.spring.model.dto.PaymentDTO;
import com.demo.spring.model.dto.PaymentMapper;
import com.demo.spring.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.demo.spring.repository.PaymentRepository;


public class PaymentServiceImplTest {

    public static final String TEST_TEST_COM = "test@test.com";
    private PaymentServiceImpl paymentServiceImpl;
    private PaymentDTO paymentDTO;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private Payment payment;

    private PaymentMapper paymentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentServiceImpl = new PaymentServiceImpl(paymentRepository);
        paymentMapper = Mappers.getMapper(PaymentMapper.class);

        paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setAmount(BigDecimal.valueOf(100));
        paymentDTO.setCreatedDate(LocalDate.now());
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setPaidDate(LocalDate.now());
        paymentDTO.setStatus(PaymentStatus.PAID);
        paymentDTO.setCurrency(Currency.USD);
    }

    @Test
    public void testGetAllPayments() {
        // given
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> payments = Arrays.asList(payment1, payment2);
        when(paymentRepository.findAll()).thenReturn(payments);

        // when
        List<Payment> result = paymentServiceImpl.getAllPayments();

        // then
        verify(paymentRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertSame(payment1, result.get(0));
        assertSame(payment2, result.get(1));
    }

    @Test
    public void testGetPaymentById() {
        // given
        Payment payment = new Payment();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        // when
        Optional<Payment> result = paymentServiceImpl.getPaymentById(1L);

        // then
        verify(paymentRepository, times(1)).findById(1L);
        assertTrue(result.isPresent());
        assertSame(payment, result.get());
    }

    @Test
    public void testDeletePayment() {
        // Given

        // When
        paymentServiceImpl.deletePayment(1L);

        // Then
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdatePaymentExistingId() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setCreatedDate(LocalDate.now());
        payment.setPayerEmail(TEST_TEST_COM);
        payment.setPaidDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setCurrency(Currency.USD);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Optional<Payment> result = paymentServiceImpl.updatePayment(1L, payment);

        // Then
        assertTrue(result.isPresent());
        assertEquals(payment.getId(), result.get().getId());
        assertEquals(payment.getCurrency(), Currency.USD);
        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testUpdatePaymentNonExistingId() {
        // Given
        Payment payment = new Payment();
        payment.setId(2L);
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<Payment> result = paymentServiceImpl.updatePayment(2L, payment);

        // Then
        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findById(2L);
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    public void testCreatePaymentNonExistingId() {
        // Given
        Payment payment = new Payment();
        payment.setId(2L);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setCreatedDate(LocalDate.now());
        payment.setPayerEmail(TEST_TEST_COM);
        payment.setPaidDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setCurrency(Currency.USD);
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());
        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Optional<Payment> result = paymentServiceImpl.createPayment(payment);

        // Then
        assertTrue(result.isPresent());
        assertEquals(payment.getId(), result.get().getId());
        assertEquals(payment.getCurrency(), Currency.USD);
        verify(paymentRepository, times(1)).findById(2L);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testCreatePaymentExistingId() {
        // Given
        Payment payment = new Payment();
        payment.setId(2L);
        when(paymentRepository.findById(2L)).thenReturn(Optional.of(payment));

        // When
        Optional<Payment> result = paymentServiceImpl.createPayment(payment);

        // Then
        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findById(2L);
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testPaymentToPaymentDTO() {
        when(payment.getId()).thenReturn(1L);
        when(payment.getAmount()).thenReturn(BigDecimal.valueOf(100));
        when(payment.getCreatedDate()).thenReturn(LocalDate.now());
        when(payment.getPayerEmail()).thenReturn(TEST_TEST_COM);
        when(payment.getPaidDate()).thenReturn(LocalDate.now());
        when(payment.getStatus()).thenReturn(PaymentStatus.PAID);
        when(payment.getCurrency()).thenReturn(Currency.USD);

        PaymentDTO result = paymentMapper.paymentToPaymentDTO(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertEquals(payment.getCreatedDate(), result.getCreatedDate());
        assertEquals(payment.getPayerEmail(), result.getPayerEmail());
        assertEquals(payment.getPaidDate(), result.getPaidDate());
        Assertions.assertEquals(payment.getStatus(), result.getStatus());
        Assertions.assertEquals(payment.getCurrency(), result.getCurrency());
    }

    @Test
    void testPaymentDTOToPayment() {
        //given
        Payment result = paymentMapper.paymentDTOToPayment(paymentDTO);

        //then
        assertEquals(paymentDTO.getId(), result.getId());
        assertEquals(paymentDTO.getAmount(), result.getAmount());
        assertEquals(paymentDTO.getCreatedDate(), result.getCreatedDate());
        assertEquals(paymentDTO.getPayerEmail(), result.getPayerEmail());
        assertEquals(paymentDTO.getPaidDate(), result.getPaidDate());
        Assertions.assertEquals(paymentDTO.getStatus(), result.getStatus());
        Assertions.assertEquals(paymentDTO.getCurrency(), result.getCurrency());
    }

}