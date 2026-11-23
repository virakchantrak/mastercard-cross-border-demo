package com.virakchantrak.mastercard_cross_border_demo.dto;


import com.virakchantrak.mastercard_cross_border_demo.constant.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentResponse {

    private UUID id;

    private UUID senderId;

    private UUID recipientId;

    private UUID bankAccountId;

    private UUID quoteId;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

