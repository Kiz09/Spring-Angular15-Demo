package com.prommt.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.prommt.demo.model.Currency;
import com.prommt.demo.model.Payment;
import com.prommt.demo.model.dto.PaymentDTO;
import com.prommt.demo.model.dto.PaymentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class IntegrationTest {

    public static final String TEST_TEST_COM = "test@test.com";

    @Autowired
    private MockMvc mockMvc;
    private PaymentDTO paymentDTO;
    private Payment payment;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(BigDecimal.valueOf(100));
        paymentDTO.setCurrency(Currency.USD);
        paymentDTO.setPayerEmail(TEST_TEST_COM);
        paymentDTO.setCreatedDate(LocalDate.now().plusDays(2));

        payment = PaymentMapper.INSTANCE.paymentDTOToPayment(paymentDTO);

        objectMapper.registerModule(new JavaTimeModule());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPaymentById_ShouldReturnOk() throws Exception {


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/payments/"+1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void createPayment_ShouldReturnCreated() throws Exception {

        String content = objectMapper.writeValueAsString(paymentDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/payments/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

    }

}