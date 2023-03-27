package com.demo.spring.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    public void testCreateCurrency() {
        Currency currency = Currency.USD;
        assertNotNull(currency);
    }

    @Test
    public void testEquals() {
        Currency currency1 = Currency.USD;
        Currency currency2 = Currency.USD;
        assertEquals(currency1, currency2);
    }

    @Test
    public void testNotEquals() {
        Currency currency1 = Currency.USD;
        Currency currency2 = Currency.EUR;
        assertNotEquals(currency1, currency2);
    }


}