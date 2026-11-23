package com.virakchantrak.mastercard_cross_border_demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRecipientBankAccountRequest {

    @NotNull(message = "Recipient ID is required")
    private UUID recipientId;

    @NotBlank(message = "Bank name is required")
    @Size(max = 150)
    private String bankName;

    @Size(max = 50)
    private String bankCode;

    @NotBlank(message = "Account number is required")
    @Size(max = 100)
    private String accountNumber;

    @NotBlank(message = "Account holder name is required")
    @Size(max = 150)
    private String accountHolderName;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3)
    private String currency;
}

