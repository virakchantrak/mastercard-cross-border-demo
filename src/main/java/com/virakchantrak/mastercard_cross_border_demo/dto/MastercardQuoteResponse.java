package com.virakchantrak.mastercard_cross_border_demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MastercardQuoteResponse {

    private Quote quote;

    @Getter
    @Setter
    public static class Quote {

        private String transactionReference;
        private Proposals proposals;
        private String paymentType;
    }

    @Getter
    @Setter
    public static class Proposals {
        private List<Proposal> proposal;
    }

    @Getter
    @Setter
    public static class Proposal {

        private String id;
        private String resourceType;
        private Boolean feesIncluded;
        private String expirationDate;
        private String quoteFxRate;
        private Amount chargedAmount;
        private Amount creditedAmount;
        private Amount principalAmount;
        private String confirmationExpiryTime;
    }

    @Getter
    @Setter
    public static class Amount {
        private String amount;
        private String currency;
    }
}
