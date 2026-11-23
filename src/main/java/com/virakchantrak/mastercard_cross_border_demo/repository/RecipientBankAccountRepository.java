package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientBankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipientBankAccountRepository
        extends JpaRepository<RecipientBankAccountEntity, UUID> {

    List<RecipientBankAccountEntity> findByRecipient_Id(UUID recipientId);

    Optional<RecipientBankAccountEntity> findByIdAndRecipient_Id(
            UUID accountId,
            UUID recipientId
    );
}
