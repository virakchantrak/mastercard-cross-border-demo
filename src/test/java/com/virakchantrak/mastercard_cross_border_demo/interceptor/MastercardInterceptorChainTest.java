package com.virakchantrak.mastercard_cross_border_demo.interceptor;

import com.virakchantrak.mastercard_cross_border_demo.config.MastercardOAuthProperties;
import com.virakchantrak.mastercard_cross_border_demo.service.MastercardJweService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;


class MastercardInterceptorChainTest {

    @Test
    void shouldEncryptPayloadBeforeOAuthSigning() throws Exception {
        // Arrange: Generate temporary RSA key pair for OAuth signing
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        PrivateKey signingKey = generator.generateKeyPair().getPrivate();

        MastercardOAuthProperties oauthProperties =
                new MastercardOAuthProperties();
        oauthProperties.setConsumerKey("test-consumer-key");

        MastercardOAuthInterceptor oauthInterceptor =
                new MastercardOAuthInterceptor(oauthProperties, signingKey);

        MastercardJweService jweService =
                Mockito.mock(MastercardJweService.class);

        MastercardJweInterceptor jweInterceptor =
                new MastercardJweInterceptor(jweService);

        HttpRequest request = Mockito.mock(HttpRequest.class);
        HttpHeaders headers = new HttpHeaders();

        Mockito.when(request.getHeaders()).thenReturn(headers);
        Mockito.when(request.getURI())
                .thenReturn(URI.create("https://sandbox.api.mastercard.com/test"));
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.POST);

        String plainText =
                "{\"transaction_reference\":\"TEST-123\"}";

        String encryptedJwe = "mock-encrypted-jwe";

        Mockito.when(jweService.encrypt(plainText))
                .thenReturn(encryptedJwe);

        ClientHttpResponse response =
                Mockito.mock(ClientHttpResponse.class);

        Mockito.when(response.getHeaders())
                .thenReturn(new HttpHeaders());

        AtomicReference<byte[]> finalBody = new AtomicReference<>();

        ClientHttpRequestExecution finalExecution =
                (finalRequest, body) -> {
                    finalBody.set(body);
                    return response;
                };

        // Act: JWE interceptor runs before OAuth interceptor
        jweInterceptor.intercept(
                request,
                plainText.getBytes(StandardCharsets.UTF_8),
                (jweRequest, encryptedBody) ->
                        oauthInterceptor.intercept(
                                jweRequest,
                                encryptedBody,
                                finalExecution
                        )
        );

        // Assert: Final body must contain encrypted payload
        String actualBody = new String(
                finalBody.get(),
                StandardCharsets.UTF_8
        );

        assertTrue(actualBody.contains("encrypted_payload"));
        assertTrue(actualBody.contains(encryptedJwe));
        assertFalse(actualBody.contains("TEST-123"));

        // Assert: OAuth Authorization header must be generated
        String authorization =
                headers.getFirst(HttpHeaders.AUTHORIZATION);

        assertNotNull(authorization);
        assertTrue(authorization.startsWith("OAuth "));

        // Verify JWE encryption was called with original payload
        Mockito.verify(jweService).encrypt(plainText);
    }
}

