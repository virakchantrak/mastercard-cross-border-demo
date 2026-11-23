package com.virakchantrak.mastercard_cross_border_demo.interceptor;


import com.virakchantrak.mastercard_cross_border_demo.service.MastercardJweService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MastercardJweInterceptorTest {

    private MastercardJweService jweService;
    private MastercardJweInterceptor interceptor;

    @BeforeEach
    void setUp() {
        jweService = Mockito.mock(MastercardJweService.class);
        interceptor = new MastercardJweInterceptor(jweService);
    }

    @Test
    void shouldEncryptRequestAndAddEncryptedHeader()
            throws Exception {

        // Arrange
        HttpRequest request = Mockito.mock(HttpRequest.class);
        HttpHeaders requestHeaders = new HttpHeaders();

        Mockito.when(request.getHeaders())
                .thenReturn(requestHeaders);

        ClientHttpRequestExecution execution =
                Mockito.mock(ClientHttpRequestExecution.class);

        ClientHttpResponse response =
                createResponse("{}", new HttpHeaders());

        Mockito.when(execution.execute(
                Mockito.same(request),
                Mockito.any(byte[].class)
        )).thenReturn(response);

        String plainText = """
                {"transaction_reference":"TEST-123"}
                """;

        String encryptedPayload = "mock-encrypted-jwe";

        Mockito.when(jweService.encrypt(plainText))
                .thenReturn(encryptedPayload);

        // Act
        interceptor.intercept(
                request,
                plainText.getBytes(StandardCharsets.UTF_8),
                execution
        );

        // Assert
        assertEquals(
                "true",
                requestHeaders.getFirst("x-encrypted")
        );

        ArgumentCaptor<byte[]> bodyCaptor =
                ArgumentCaptor.forClass(byte[].class);

        Mockito.verify(execution).execute(
                Mockito.same(request),
                bodyCaptor.capture()
        );

        String actualBody =
                new String(bodyCaptor.getValue(), StandardCharsets.UTF_8);

        assertTrue(actualBody.contains("encrypted_payload"));
        assertTrue(actualBody.contains(encryptedPayload));

        assertEquals(
                bodyCaptor.getValue().length,
                requestHeaders.getContentLength()
        );

        Mockito.verify(jweService).encrypt(plainText);
    }

    @Test
    void shouldDecryptEncryptedResponse()
            throws Exception {

        // Arrange
        HttpRequest request = Mockito.mock(HttpRequest.class);

        ClientHttpRequestExecution execution =
                Mockito.mock(ClientHttpRequestExecution.class);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-encrypted", "true");

        String encryptedPayload =
                """
                {"encrypted_payload":{"data":"mock-encrypted-jwe"}}
                """;

        ClientHttpResponse response =
                createResponse(encryptedPayload, responseHeaders);

        Mockito.when(execution.execute(
                Mockito.same(request),
                Mockito.any(byte[].class)
        )).thenReturn(response);

        String decryptedPayload =
                """
                {"transaction_reference":"TEST-123"}
                """;

        Mockito.when(jweService.decrypt("mock-encrypted-jwe"))
                .thenReturn(decryptedPayload);

        // Act
        ClientHttpResponse actualResponse =
                interceptor.intercept(request, new byte[0], execution);

        // Assert
        String actualBody = new String(
                actualResponse.getBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        assertEquals(decryptedPayload, actualBody);

        assertNull(
                actualResponse.getHeaders().getFirst("x-encrypted")
        );

        assertEquals(
                decryptedPayload.getBytes(StandardCharsets.UTF_8).length,
                actualResponse.getHeaders().getContentLength()
        );

        Mockito.verify(jweService).decrypt("mock-encrypted-jwe");
    }

    @Test
    void shouldNotDecryptUnencryptedResponse()
            throws Exception {

        // Arrange
        HttpRequest request = Mockito.mock(HttpRequest.class);

        ClientHttpRequestExecution execution =
                Mockito.mock(ClientHttpRequestExecution.class);

        HttpHeaders responseHeaders = new HttpHeaders();

        ClientHttpResponse response =
                createResponse(
                        """
                        {"message":"validation error"}
                        """,
                        responseHeaders
                );

        Mockito.when(execution.execute(
                Mockito.same(request),
                Mockito.any(byte[].class)
        )).thenReturn(response);

        // Act
        ClientHttpResponse actualResponse =
                interceptor.intercept(request, new byte[0], execution);

        // Assert
        assertSame(response, actualResponse);

        Mockito.verifyNoInteractions(jweService);
    }

    private ClientHttpResponse createResponse(
            String body,
            HttpHeaders headers
    ) throws IOException {

        ClientHttpResponse response =
                Mockito.mock(ClientHttpResponse.class);

        Mockito.when(response.getHeaders())
                .thenReturn(headers);

        Mockito.when(response.getBody())
                .thenAnswer(invocation ->
                        new ByteArrayInputStream(
                                body.getBytes(StandardCharsets.UTF_8)
                        )
                );

        Mockito.when(response.getStatusCode())
                .thenReturn(HttpStatusCode.valueOf(200));

        Mockito.when(response.getStatusText())
                .thenReturn("OK");

        return response;
    }
}

