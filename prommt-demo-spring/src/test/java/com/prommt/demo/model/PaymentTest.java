package com.prommt.demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    public static final String TEST_EXAMPLE_COM = "test@example.com";

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment();
        assertNotNull(payment);
    }

    @Test
    public void testSetAndGetId() {
        Payment payment = new Payment();
        payment.setId(1L);
        assertEquals(1L, payment.getId());
    }

    @Test
    public void testSetAndGetCreatedDate() {
        Payment payment = new Payment();
        LocalDate createdDate = LocalDate.now();
        payment.setCreatedDate(createdDate);
        assertEquals(createdDate, payment.getCreatedDate());
    }

    @Test
    public void testSetAndGetPayerEmail() {
        Payment payment = new Payment();
        String payerEmail = TEST_EXAMPLE_COM;
        payment.setPayerEmail(payerEmail);
        assertEquals(payerEmail, payment.getPayerEmail());
    }

    @Test
    public void testSetAndGetStatus() {
        Payment payment = new Payment();
        PaymentStatus status = PaymentStatus.CREATED;
        payment.setStatus(status);
        assertEquals(status, payment.getStatus());
    }

    @Test
    public void testSetAndGetCurrency() {
        Payment payment = new Payment();
        Currency currency = Currency.USD;
        payment.setCurrency(currency);
        assertEquals(currency, payment.getCurrency());
    }

    @Test
    public void testSetAndGetAmount() {
        Payment payment = new Payment();
        int amount = 100;
        payment.setAmount(amount);
        assertEquals(amount, payment.getAmount());
    }

    @Test
    public void testSetAndGetPaidDate() {
        Payment payment = new Payment();
        LocalDate paidDate = LocalDate.now();
        payment.setPaidDate(paidDate);
        assertEquals(paidDate, payment.getPaidDate());
    }


}