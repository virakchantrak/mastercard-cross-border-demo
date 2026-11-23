package com.virakchantrak.mastercard_cross_border_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MastercardQuoteRequest {

    @JsonProperty("quoterequest")
    private QuoteRequest quoteRequest;

    @Getter
    @Setter
    public static class QuoteRequest {

        private String transactionReference;
        private String senderAccountUri;
        private String recipientAccountUri;
        private PaymentAmount paymentAmount;
        private String paymentOriginationCountry;
        private String paymentType;
        private QuoteType quoteType;
    }

    @Getter
    @Setter
    public static class PaymentAmount {
        private String amount;
        private String currency;
    }

    @Getter
    @Setter
    public static class QuoteType {
        private Forward forward;
    }

    @Getter
    @Setter
    public static class Forward {
        private String feesIncluded;
        private String receiverCurrency;
    }
}
