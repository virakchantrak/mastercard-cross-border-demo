package com.virakchantrak.mastercard_cross_border_demo.interceptor;


import com.mastercard.developer.oauth.OAuth;
import com.virakchantrak.mastercard_cross_border_demo.config.MastercardOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@Component
@RequiredArgsConstructor
public class MastercardOAuthInterceptor implements ClientHttpRequestInterceptor {

    private final MastercardOAuthProperties properties;
    private final PrivateKey mastercardSigningKey;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        String payload = new String(body, StandardCharsets.UTF_8);

        String authorizationHeader = OAuth.getAuthorizationHeader(
                request.getURI(),
                request.getMethod().name(),
                payload,
                StandardCharsets.UTF_8,
                properties.getConsumerKey(),
                mastercardSigningKey
        );

        request.getHeaders().set(
                HttpHeaders.AUTHORIZATION,
                authorizationHeader
        );

        return execution.execute(request, body);
    }
}

