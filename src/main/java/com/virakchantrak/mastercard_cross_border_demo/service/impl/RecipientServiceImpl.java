package com.virakchantrak.mastercard_cross_border_demo.service.impl;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.RecipientEntity;
import com.virakchantrak.mastercard_cross_border_demo.entity.SenderEntity;
import com.virakchantrak.mastercard_cross_border_demo.mapper.RecipientMapper;
import com.virakchantrak.mastercard_cross_border_demo.repository.RecipientRepository;
import com.virakchantrak.mastercard_cross_border_demo.repository.SenderRepository;
import com.virakchantrak.mastercard_cross_border_demo.service.RecipientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipientServiceImpl implements RecipientService {

    private final RecipientRepository recipientRepository;
    private final SenderRepository senderRepository;
    private final RecipientMapper recipientMapper;

    @Override
    public RecipientResponse createRecipient(CreateRecipientRequest request) {

        SenderEntity sender = senderRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sender not found with ID: " + request.getSenderId()
                ));

        RecipientEntity recipient = recipientMapper.toEntity(request);

        recipient.setSender(sender);

        RecipientEntity savedRecipient = recipientRepository.save(recipient);

        return recipientMapper.toResponse(savedRecipient);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipientResponse getRecipientById(UUID senderId, UUID recipientId) {

        RecipientEntity recipient = recipientRepository
                .findByIdAndSender_Id(recipientId, senderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recipient not found with ID: " + recipientId
                ));

        return recipientMapper.toResponse(recipient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipientResponse> getRecipientsBySenderId(UUID senderId) {

        if (!senderRepository.existsById(senderId)) {
            throw new EntityNotFoundException(
                    "Sender not found with ID: " + senderId
            );
        }

        return recipientRepository.findBySender_Id(senderId)
                .stream()
                .map(recipientMapper::toResponse)
                .toList();
    }
}
