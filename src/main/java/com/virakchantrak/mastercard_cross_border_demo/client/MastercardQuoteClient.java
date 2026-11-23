package com.virakchantrak.mastercard_cross_border_demo.client;

import com.virakchantrak.mastercard_cross_border_demo.config.MastercardApiProperties;
import com.virakchantrak.mastercard_cross_border_demo.dto.MastercardQuoteRequest;
import com.virakchantrak.mastercard_cross_border_demo.dto.MastercardQuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MastercardQuoteClient {

    private final RestClient mastercardRestClient;
    private final MastercardApiProperties mastercardApiProperties;

    public MastercardQuoteResponse createQuote(
            MastercardQuoteRequest request
    ) {
        return mastercardRestClient
                .post()
                .uri(
                        "/send/v1/partners/{partnerId}/crossborder/quotes",
                        mastercardApiProperties.getPartnerId()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(MastercardQuoteResponse.class);
    }
}
