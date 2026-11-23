package com.virakchantrak.mastercard_cross_border_demo.service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEObject;
import com.virakchantrak.mastercard_cross_border_demo.config.MastercardApiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class MastercardJweServiceTest {

    private MastercardJweService jweService;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        X509Certificate certificate =
                Mockito.mock(X509Certificate.class);

        Mockito.when(certificate.getPublicKey())
                .thenReturn(keyPair.getPublic());

        MastercardApiProperties properties =
                new MastercardApiProperties();

        MastercardApiProperties.Encryption encryption =
                new MastercardApiProperties.Encryption();

        encryption.setFingerprint("test-fingerprint");

        properties.setEncryption(encryption);

        jweService = new MastercardJweService(
                certificate,
                properties,
                keyPair.getPrivate()
        );
    }

    @Test
    void shouldEncryptAndDecryptPayloadSuccessfully()
            throws Exception {

        // Arrange
        String originalPayload = """
                {
                    "transaction_reference": "TEST-123",
                    "amount": "100.00",
                    "currency": "USD"
                }
                """;

        // Act
        String encryptedPayload =
                jweService.encrypt(originalPayload);

        String decryptedPayload =
                jweService.decrypt(encryptedPayload);

        // Assert
        assertNotNull(encryptedPayload);
        assertFalse(encryptedPayload.isBlank());

        assertEquals(originalPayload, decryptedPayload);
    }

    @Test
    void shouldGenerateCorrectJweHeader()
            throws Exception {

        // Arrange
        String originalPayload = "{\"message\":\"hello\"}";

        // Act
        String encryptedPayload =
                jweService.encrypt(originalPayload);

        JWEObject jweObject =
                JWEObject.parse(encryptedPayload);

        // Assert
        assertEquals(
                JWEAlgorithm.RSA_OAEP_256,
                jweObject.getHeader().getAlgorithm()
        );

        assertEquals(
                EncryptionMethod.A256GCM,
                jweObject.getHeader().getEncryptionMethod()
        );

        assertEquals(
                "application/json",
                jweObject.getHeader().getContentType()
        );

        assertEquals(
                "test-fingerprint",
                jweObject.getHeader().getKeyID()
        );
    }
}
