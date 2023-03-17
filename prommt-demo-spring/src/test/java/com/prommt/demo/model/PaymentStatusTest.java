package com.prommt.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    public void testCreatePaymentStatus() {
        PaymentStatus paymentStatus = PaymentStatus.PAID;
        assertNotNull(paymentStatus);
    }

    @Test
    public void testEquals() {
        PaymentStatus paymentStatus1 = PaymentStatus.PAID;
        PaymentStatus paymentStatus2 = PaymentStatus.PAID;
        assertEquals(paymentStatus1, paymentStatus2);
    }

    @Test
    public void testNotEquals() {
        PaymentStatus paymentStatus1 = PaymentStatus.PAID;
        PaymentStatus paymentStatus2 = PaymentStatus.CREATED;
        assertNotEquals(paymentStatus1, paymentStatus2);
    }

}