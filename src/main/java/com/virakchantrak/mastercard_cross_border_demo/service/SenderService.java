package com.virakchantrak.mastercard_cross_border_demo.service;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateSenderRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.SenderResponse;

import java.util.List;
import java.util.UUID;

public interface SenderService {

    SenderResponse createSender(CreateSenderRequest request);

    SenderResponse getSenderById(UUID id);

    List<SenderResponse> getAllSenders();
}