package com.virakchantrak.mastercard_cross_border_demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SenderResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String phone;

    private String countryCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
