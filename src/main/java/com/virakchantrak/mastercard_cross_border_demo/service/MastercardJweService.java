package com.virakchantrak.mastercard_cross_border_demo.service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.virakchantrak.mastercard_cross_border_demo.config.MastercardApiProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.cert.X509Certificate;
import java.text.ParseException;

import java.security.interfaces.RSAPrivateKey;

@Service
public class MastercardJweService {

    private final X509Certificate mastercardEncryptionCertificate;
    private final MastercardApiProperties properties;
    private final PrivateKey mastercardDecryptionPrivateKey;


    public MastercardJweService(
            X509Certificate mastercardEncryptionCertificate,
            MastercardApiProperties properties,
            @Qualifier("mastercardDecryptionPrivateKey")
            PrivateKey mastercardDecryptionPrivateKey
    ) {
        if (!(mastercardEncryptionCertificate.getPublicKey()
                instanceof RSAPublicKey)) {
            throw new IllegalStateException(
                    "Mastercard encryption certificate must contain an RSA public key"
            );
        }

        if (!(mastercardDecryptionPrivateKey
                instanceof RSAPrivateKey)) {
            throw new IllegalStateException(
                    "Mastercard decryption key must be an RSA private key"
            );
        }

        this.mastercardEncryptionCertificate =
                mastercardEncryptionCertificate;
        this.properties = properties;
        this.mastercardDecryptionPrivateKey =
                mastercardDecryptionPrivateKey;
    }


    public String encrypt(String plainText) throws JOSEException {

        RSAPublicKey publicKey = (RSAPublicKey)
                mastercardEncryptionCertificate.getPublicKey();

        JWEHeader header = new JWEHeader.Builder(
                JWEAlgorithm.RSA_OAEP_256,
                EncryptionMethod.A256GCM
        )
                .contentType("application/json")
                .keyID(properties.getEncryption().getFingerprint())
                .build();

        JWEObject jweObject = new JWEObject(
                header,
                new Payload(plainText)
        );

        jweObject.encrypt(new RSAEncrypter(publicKey));

        return jweObject.serialize();
    }


    public String decrypt(String encryptedPayload)
            throws JOSEException, ParseException {

        JWEObject jweObject = JWEObject.parse(encryptedPayload);

        JWEHeader header = jweObject.getHeader();

        // 1. Validate key encryption algorithm
        if (!JWEAlgorithm.RSA_OAEP_256.equals(
                header.getAlgorithm()
        )) {
            throw new JOSEException(
                    "Unsupported JWE key encryption algorithm"
            );
        }

        // 2. Validate content encryption algorithm
        if (!EncryptionMethod.A256GCM.equals(
                header.getEncryptionMethod()
        )) {
            throw new JOSEException(
                    "Unsupported JWE content encryption algorithm"
            );
        }

        // 3. Validate Key ID exists
        if (header.getKeyID() == null
                || header.getKeyID().isBlank()) {
            throw new JOSEException(
                    "Missing JWE Key ID (kid)"
            );
        }

        // 4. Decrypt payload
        jweObject.decrypt(
                new RSADecrypter(mastercardDecryptionPrivateKey)
        );

        return jweObject.getPayload().toString();
    }

}
