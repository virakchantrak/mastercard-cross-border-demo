package com.virakchantrak.mastercard_cross_border_demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotNull(message = "Quote ID is required")
    private UUID quoteId;

    @NotNull(message = "Bank account ID is required")
    private UUID bankAccountId;
}

