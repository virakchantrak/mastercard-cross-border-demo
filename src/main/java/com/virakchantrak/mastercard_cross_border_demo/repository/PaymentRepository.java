package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.constant.PaymentStatus;
import com.virakchantrak.mastercard_cross_border_demo.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findBySender_Id(UUID senderId);

    Optional<PaymentEntity> findByIdAndSender_Id(
            UUID paymentId,
            UUID senderId
    );

    Optional<PaymentEntity> findByQuote_Id(UUID quoteId);

    List<PaymentEntity> findByStatus(PaymentStatus status);
}
