package com.virakchantrak.mastercard_cross_border_demo.repository;

import com.virakchantrak.mastercard_cross_border_demo.entity.SenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SenderRepository extends JpaRepository<SenderEntity, UUID> {

    Optional<SenderEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
