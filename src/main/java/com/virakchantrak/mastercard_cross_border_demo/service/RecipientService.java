package com.virakchantrak.mastercard_cross_border_demo.service;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateRecipientRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.RecipientResponse;

import java.util.List;
import java.util.UUID;

public interface RecipientService {

    RecipientResponse createRecipient(CreateRecipientRequest request);

    RecipientResponse getRecipientById(UUID senderId, UUID recipientId);

    List<RecipientResponse> getRecipientsBySenderId(UUID senderId);
}
