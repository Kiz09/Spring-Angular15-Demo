package com.prommt.demo.model.dto;

import com.prommt.demo.model.Currency;
import com.prommt.demo.model.Payment;
import com.prommt.demo.model.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    PaymentMapper paymentMapper;
    public static final String TEST_TEST_COM = "test@test.com";

    @Test
    public void testCreatePaymentDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();
        assertNotNull(paymentDTO);
    }

    @Test
    public void testSetAndGetId() {
        PaymentDTO paymentDTO = new PaymentDTO();
        Long id = 1L;
        paymentDTO.setId(id);
        paymentDTO.setAmount(BigDecimal.valueOf(100));
        paymentDTO.setCreatedDate(LocalDate.now());
        paymentDTO.setPaidDate(LocalDate.now());
        paymentDTO.setCurrency(Currency.EUR);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setStatus(PaymentStatus.CREATED);
        assertEquals(id, paymentDTO.getId());
        assertEquals(paymentDTO.getAmount(), BigDecimal.valueOf(100));
        assertNotNull(paymentDTO.getCreatedDate());
        assertNotNull(paymentDTO.getPaidDate());
        assertEquals(paymentDTO.getCurrency(), Currency.EUR);
        assertEquals(paymentDTO.getStatus(), PaymentStatus.CREATED);
        assertEquals(paymentDTO.getPayerEmail(), TEST_TEST_COM);
    }

    @Test
    public void testSetAndGetCreatedDate() {
        PaymentDTO paymentDTO = new PaymentDTO();
        LocalDate createdDate = LocalDate.now();
        paymentDTO.setCreatedDate(createdDate);
        assertEquals(createdDate, paymentDTO.getCreatedDate());
    }

    @Test
    public void testSetNullPaymentToPaymentDTO_null() {
        PaymentDTO paymentDTO = PaymentMapper.INSTANCE.paymentToPaymentDTO(null);

        assertNull(paymentDTO);
    }

    @Test
    public void testSetNullPaymentDTOToPayment_null() {
        Payment payment = PaymentMapper.INSTANCE.paymentDTOToPayment(null);

        assertNull(payment);
    }

}