package com.virakchantrak.mastercard_cross_border_demo.interceptor;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.virakchantrak.mastercard_cross_border_demo.service.MastercardJweService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MastercardJweInterceptor
        implements ClientHttpRequestInterceptor {

    private final MastercardJweService jweService;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        // 1. Encrypt request body when present
        byte[] encryptedBody = body;

        if (body.length > 0) {
            try {
                String plainText =
                        new String(body, StandardCharsets.UTF_8);

                String encryptedPayload =
                        jweService.encrypt(plainText);

                String wrapper = """
                        {"encrypted_payload":{"data":"%s"}}
                        """.formatted(encryptedPayload).trim();

                encryptedBody = wrapper.getBytes(StandardCharsets.UTF_8);

                request.getHeaders().set("x-encrypted", "true");
                request.getHeaders().setContentLength(encryptedBody.length);

            } catch (JOSEException exception) {
                throw new IOException(
                        "Failed to encrypt Mastercard request payload",
                        exception
                );
            }
        }

        // 2. Execute request (OAuth interceptor signs encrypted body)
        ClientHttpResponse response =
                execution.execute(request, encryptedBody);

        // 3. Decrypt response only when Mastercard marks it encrypted
        if (!"true".equalsIgnoreCase(
                response.getHeaders().getFirst("x-encrypted")
        )) {
            return response;
        }

        try {
            byte[] responseBody = response.getBody().readAllBytes();

            Map<String, Object> responseJson =
                    JSONObjectUtils.parse(
                            new String(responseBody, StandardCharsets.UTF_8)
                    );

            Map<String, Object> encryptedPayload =
                    JSONObjectUtils.getJSONObject(
                            responseJson,
                            "encrypted_payload"
                    );

            String encryptedData =
                    JSONObjectUtils.getString(
                            encryptedPayload,
                            "data"
                    );

            String decryptedPayload =
                    jweService.decrypt(encryptedData);

            return new DecryptedClientHttpResponse(
                    response,
                    decryptedPayload.getBytes(StandardCharsets.UTF_8)
            );

        } catch (ParseException | JOSEException exception) {
            response.close();

            throw new IOException(
                    "Failed to decrypt Mastercard response payload",
                    exception
            );
        }
    }

    private static class DecryptedClientHttpResponse
            implements ClientHttpResponse {

        private final ClientHttpResponse delegate;
        private final byte[] body;
        private final HttpHeaders headers;

        private DecryptedClientHttpResponse(
                ClientHttpResponse delegate,
                byte[] body
        ) {
            this.delegate = delegate;
            this.body = body;

            this.headers = new HttpHeaders();
            this.headers.putAll(delegate.getHeaders());
            this.headers.remove("x-encrypted");
            this.headers.setContentLength(body.length);
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return delegate.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return delegate.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public void close() {
            delegate.close();
        }
    }
}

