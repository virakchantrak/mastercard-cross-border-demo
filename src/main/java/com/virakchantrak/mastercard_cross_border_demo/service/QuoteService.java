package com.virakchantrak.mastercard_cross_border_demo.service;

import com.virakchantrak.mastercard_cross_border_demo.dto.CreateQuoteRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.QuoteResponse;

import java.util.List;
import java.util.UUID;

public interface QuoteService {

    QuoteResponse createQuote(CreateQuoteRequest request);

    QuoteResponse getQuoteById(UUID senderId, UUID quoteId);

    List<QuoteResponse> getQuotesBySenderId(UUID senderId);
}
