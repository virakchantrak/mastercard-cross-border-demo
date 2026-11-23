package com.virakchantrak.mastercard_cross_border_demo.service.impl;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateSenderRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.SenderResponse;
import com.virakchantrak.mastercard_cross_border_demo.entity.SenderEntity;
import com.virakchantrak.mastercard_cross_border_demo.mapper.SenderMapper;
import com.virakchantrak.mastercard_cross_border_demo.repository.SenderRepository;
import com.virakchantrak.mastercard_cross_border_demo.service.SenderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;
    private final SenderMapper senderMapper;

    @Override
    public SenderResponse createSender(CreateSenderRequest request) {

        if (request.getEmail() != null
                && !request.getEmail().isBlank()
                && senderRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Sender email already exists: " + request.getEmail()
            );
        }

        SenderEntity sender = senderMapper.toEntity(request);

        SenderEntity savedSender = senderRepository.save(sender);

        return senderMapper.toResponse(savedSender);
    }

    @Override
    @Transactional(readOnly = true)
    public SenderResponse getSenderById(UUID id) {

        SenderEntity sender = senderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sender not found with ID: " + id
                ));

        return senderMapper.toResponse(sender);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SenderResponse> getAllSenders() {

        return senderRepository.findAll()
                .stream()
                .map(senderMapper::toResponse)
                .toList();
    }
}
