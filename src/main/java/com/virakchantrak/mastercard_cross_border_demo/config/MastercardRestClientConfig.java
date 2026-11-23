package com.virakchantrak.mastercard_cross_border_demo.config;

import com.virakchantrak.mastercard_cross_border_demo.interceptor.MastercardJweInterceptor;
import com.virakchantrak.mastercard_cross_border_demo.interceptor.MastercardOAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class MastercardRestClientConfig {

    @Bean
    public RestClient mastercardRestClient(
            RestClient.Builder builder,
            MastercardApiProperties properties,
            MastercardJweInterceptor jweInterceptor,
            MastercardOAuthInterceptor oauthInterceptor
    ) {
        return builder
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                // JWE must run before OAuth
                .requestInterceptor(jweInterceptor)
                .requestInterceptor(oauthInterceptor)
                .build();
    }
}
