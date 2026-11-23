package com.virakchantrak.mastercard_cross_border_demo.dto;


import com.virakchantrak.mastercard_cross_border_demo.constant.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {

    private UUID id;

    private UUID paymentId;

    private String mastercardReference;

    private TransactionStatus status;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

