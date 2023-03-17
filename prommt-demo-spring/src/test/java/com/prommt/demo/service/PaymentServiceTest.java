package com.prommt.demo.service;

import com.prommt.demo.model.Currency;
import com.prommt.demo.model.PaymentStatus;
import org.junit.jupiter.api.Test;

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

import com.prommt.demo.model.Payment;
import com.prommt.demo.model.dto.PaymentDTO;
import com.prommt.demo.model.dto.PaymentMapper;
import com.prommt.demo.repository.PaymentRepository;


public class PaymentServiceTest {

    public static final String TEST_TEST_COM = "test@test.com";
    private PaymentService paymentService;
    private PaymentDTO paymentDTO;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private Payment payment;

    private PaymentMapper paymentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentRepository);
        paymentMapper = Mappers.getMapper(PaymentMapper.class);

        paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setAmount(100);
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
        List<Payment> result = paymentService.getAllPayments();

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
        Optional<Payment> result = paymentService.getPaymentById(1L);

        // then
        verify(paymentRepository, times(1)).findById(1L);
        assertTrue(result.isPresent());
        assertSame(payment, result.get());
    }

    @Test
    public void testDeletePayment() {
        // Given

        // When
        paymentService.deletePayment(1L);

        // Then
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdatePaymentExistingId() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);
        payment.setCreatedDate(LocalDate.now());
        payment.setPayerEmail(TEST_TEST_COM);
        payment.setPaidDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setCurrency(Currency.USD);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Optional<Payment> result = paymentService.updatePayment(1L, payment);

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
        Optional<Payment> result = paymentService.updatePayment(2L, payment);

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
        payment.setAmount(100);
        payment.setCreatedDate(LocalDate.now());
        payment.setPayerEmail(TEST_TEST_COM);
        payment.setPaidDate(LocalDate.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setCurrency(Currency.USD);
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());
        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Optional<Payment> result = paymentService.createPayment(payment);

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
        Optional<Payment> result = paymentService.createPayment(payment);

        // Then
        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findById(2L);
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testPaymentToPaymentDTO() {
        when(payment.getId()).thenReturn(1L);
        when(payment.getAmount()).thenReturn(100);
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
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getCurrency(), result.getCurrency());
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
        assertEquals(paymentDTO.getStatus(), result.getStatus());
        assertEquals(paymentDTO.getCurrency(), result.getCurrency());
    }

}