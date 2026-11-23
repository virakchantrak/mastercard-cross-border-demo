package com.virakchantrak.mastercard_cross_border_demo.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateQuoteRequest {

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotNull(message = "Recipient ID is required")
    private UUID recipientId;

    @NotBlank(message = "Source currency is required")
    @Size(min = 3, max = 3)
    private String sourceCurrency;

    @NotBlank(message = "Target currency is required")
    @Size(min = 3, max = 3)
    private String targetCurrency;

    @NotNull(message = "Source amount is required")
    @DecimalMin(value = "0.0001", message = "Source amount must be greater than zero")
    private BigDecimal sourceAmount;
}

