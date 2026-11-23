package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipientMapper {

    @Mapping(target = "sender", ignore = true)
    RecipientEntity toEntity(CreateRecipientRequest request);

    @Mapping(target = "senderId", source = "sender.id")
    RecipientResponse toResponse(RecipientEntity entity);
}
