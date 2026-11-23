package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreatePaymentRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.PaymentResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    @Mapping(target = "bankAccount", ignore = true)
    @Mapping(target = "quote", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "status", ignore = true)
    PaymentEntity toEntity(CreatePaymentRequest request);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "bankAccountId", source = "bankAccount.id")
    @Mapping(target = "quoteId", source = "quote.id")
    PaymentResponse toResponse(PaymentEntity entity);
}
