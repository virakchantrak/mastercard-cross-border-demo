package com.virakchantrak.mastercard_cross_border_demo.service;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientBankAccountRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientBankAccountResponse;

import java.util.List;
import java.util.UUID;

public interface RecipientBankAccountService {

    RecipientBankAccountResponse createBankAccount(
            CreateRecipientBankAccountRequest request
    );

    RecipientBankAccountResponse getBankAccountById(
            UUID recipientId,
            UUID bankAccountId
    );

    List<RecipientBankAccountResponse> getBankAccountsByRecipientId(
            UUID recipientId
    );
}
