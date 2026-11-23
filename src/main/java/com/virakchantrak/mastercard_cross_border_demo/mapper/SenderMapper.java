package com.virakchantrak.mastercard_cross_border_demo.mapper;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateSenderRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.SenderResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.SenderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SenderMapper {

    SenderEntity toEntity(CreateSenderRequest request);

    SenderResponse toResponse(SenderEntity entity);
}
