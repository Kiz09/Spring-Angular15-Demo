package com.demo.spring.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

import static org.junit.jupiter.api.Assertions.*;

class PaymentControllerExceptionHandlerTest {

    public static final String TEST_EXCEPTION = "Test exception";

    @Test
    public void testHandlerExceptionShouldReturnErrorResponse() {
        // Given
        Exception exception = new Exception(TEST_EXCEPTION);
        PaymentControllerExceptionHandler handler = new PaymentControllerExceptionHandler();

        // When
        ErrorResponse errorResponse = handler.handlerException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getStatusCode());
        assertEquals(TEST_EXCEPTION, errorResponse.getBody().getDetail());
    }

}