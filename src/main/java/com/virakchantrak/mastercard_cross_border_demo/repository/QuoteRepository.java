package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.constant.QuoteStatus;
import com.virakchantrak.mastercard_cross_border_demo.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<QuoteEntity, UUID> {

    List<QuoteEntity> findBySender_Id(UUID senderId);

    Optional<QuoteEntity> findByIdAndSender_Id(
            UUID quoteId,
            UUID senderId
    );

    List<QuoteEntity> findByStatus(QuoteStatus status);

    List<QuoteEntity> findByExpiresAtBefore(LocalDateTime dateTime);
}
