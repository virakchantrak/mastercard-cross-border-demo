package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipientRepository extends JpaRepository<RecipientEntity, UUID> {

    List<RecipientEntity> findBySender_Id(UUID senderId);

    Optional<RecipientEntity> findByIdAndSender_Id(
            UUID recipientId,
            UUID senderId
    );
}
