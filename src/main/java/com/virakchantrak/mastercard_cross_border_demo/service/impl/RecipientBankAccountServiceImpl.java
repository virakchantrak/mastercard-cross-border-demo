package com.virakchantrak.mastercard_cross_border_demo.service.impl;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientBankAccountRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientBankAccountResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientBankAccountEntity;
import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientEntity;
import com.virakchantrak.mastercard_cross_border_demo.mapper.RecipientBankAccountMapper;
import com.virakchantrak.mastercard_cross_border_demo.repository.RecipientBankAccountRepository;
import com.virakchantrak.mastercard_cross_border_demo.repository.RecipientRepository;
import com.virakchantrak.mastercard_cross_border_demo.service.RecipientBankAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipientBankAccountServiceImpl
        implements RecipientBankAccountService {

    private final RecipientBankAccountRepository bankAccountRepository;
    private final RecipientRepository recipientRepository;
    private final RecipientBankAccountMapper bankAccountMapper;

    @Override
    public RecipientBankAccountResponse createBankAccount(
            CreateRecipientBankAccountRequest request) {

        RecipientEntity recipient = recipientRepository
                .findById(request.getRecipientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recipient not found with ID: " + request.getRecipientId()
                ));

        RecipientBankAccountEntity bankAccount =
                bankAccountMapper.toEntity(request);

        bankAccount.setRecipient(recipient);

        RecipientBankAccountEntity savedBankAccount =
                bankAccountRepository.save(bankAccount);

        return bankAccountMapper.toResponse(savedBankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipientBankAccountResponse getBankAccountById(
            UUID recipientId,
            UUID bankAccountId) {

        RecipientBankAccountEntity bankAccount = bankAccountRepository
                .findByIdAndRecipient_Id(bankAccountId, recipientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Bank account not found with ID: " + bankAccountId
                ));

        return bankAccountMapper.toResponse(bankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipientBankAccountResponse> getBankAccountsByRecipientId(
            UUID recipientId) {

        if (!recipientRepository.existsById(recipientId)) {
            throw new EntityNotFoundException(
                    "Recipient not found with ID: " + recipientId
            );
        }

        return bankAccountRepository.findByRecipient_Id(recipientId)
                .stream()
                .map(bankAccountMapper::toResponse)
                .toList();
    }
}
