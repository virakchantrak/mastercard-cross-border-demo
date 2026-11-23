package com.virakchantrak.mastercard_cross_border_demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RecipientResponse {

    private UUID id;

    private UUID senderId;

    private String fullName;

    private String phone;

    private String countryCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

