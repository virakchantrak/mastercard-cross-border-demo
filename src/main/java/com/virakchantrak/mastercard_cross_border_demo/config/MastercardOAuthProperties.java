package com.virakchantrak.mastercard_cross_border_demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mastercard.oauth")
public class MastercardOAuthProperties {

    private String consumerKey;

    private String signingKeyPath;

    private String keyAlias;

    private String keystorePassword;
}

