package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateQuoteRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.QuoteResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.QuoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    @Mapping(target = "targetAmount", ignore = true)
    @Mapping(target = "exchangeRate", ignore = true)
    @Mapping(target = "fee", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "transactionReference", ignore = true)
    @Mapping(target = "proposalId", ignore = true)
    QuoteEntity toEntity(CreateQuoteRequest request);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    QuoteResponse toResponse(QuoteEntity entity);
}
