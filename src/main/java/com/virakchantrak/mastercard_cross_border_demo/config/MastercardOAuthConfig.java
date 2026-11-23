package com.virakchantrak.mastercard_cross_border_demo.config;


import com.mastercard.developer.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;

@Configuration
@RequiredArgsConstructor
public class MastercardOAuthConfig {

    private final MastercardOAuthProperties properties;

    @Bean
    public PrivateKey mastercardSigningKey() throws Exception {
        return AuthenticationUtils.loadSigningKey(
                properties.getSigningKeyPath(),
                properties.getKeyAlias(),
                properties.getKeystorePassword()
        );
    }
}

