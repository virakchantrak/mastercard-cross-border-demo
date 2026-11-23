package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientBankAccountRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientBankAccountResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientBankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipientBankAccountMapper {

    @Mapping(target = "recipient", ignore = true)
    RecipientBankAccountEntity toEntity(
            CreateRecipientBankAccountRequest request
    );

    @Mapping(target = "recipientId", source = "recipient.id")
    RecipientBankAccountResponse toResponse(
            RecipientBankAccountEntity entity
    );
}
