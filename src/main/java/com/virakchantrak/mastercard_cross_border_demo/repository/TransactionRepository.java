package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.constant.TransactionStatus;
import com.virakchantrak.mastercard_cross_border_demo.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    Optional<TransactionEntity> findByPayment_Id(UUID paymentId);

    Optional<TransactionEntity> findByMastercardReference(
            String mastercardReference
    );

    List<TransactionEntity> findByStatus(TransactionStatus status);
}
