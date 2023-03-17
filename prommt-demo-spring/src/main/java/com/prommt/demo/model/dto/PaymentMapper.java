package com.prommt.demo.model.dto;

import com.prommt.demo.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDTO paymentToPaymentDTO(Payment payment);
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);

}
