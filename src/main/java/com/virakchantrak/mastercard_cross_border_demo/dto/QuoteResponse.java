package com.virakchantrak.mastercard_cross_border_demo.dto;


import com.virakchantrak.mastercard_cross_border_demo.constant.QuoteStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class QuoteResponse {

    private UUID id;

    private String transactionReference;

    private String proposalId;

    private UUID senderId;

    private UUID recipientId;

    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal sourceAmount;

    private BigDecimal targetAmount;

    private BigDecimal exchangeRate;

    private BigDecimal fee;

    private QuoteStatus status;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;
}
