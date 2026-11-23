package com.virakchantrak.mastercard_cross_border_demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RecipientBankAccountResponse {

    private UUID id;

    private UUID recipientId;

    private String bankName;

    private String bankCode;

    private String accountNumber;

    private String accountHolderName;

    private String currency;

    private LocalDateTime createdAt;
}

