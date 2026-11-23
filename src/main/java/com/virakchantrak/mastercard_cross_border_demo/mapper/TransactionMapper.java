package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.TransactionResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "paymentId", source = "payment.id")
    TransactionResponse toResponse(TransactionEntity entity);
}
