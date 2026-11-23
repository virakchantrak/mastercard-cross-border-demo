package com.virakchantrak.mastercard_cross_border_demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRecipientRequest {

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotBlank(message = "Full name is required")
    @Size(max = 150)
    private String fullName;

    @Size(max = 30)
    private String phone;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3)
    private String countryCode;
}

