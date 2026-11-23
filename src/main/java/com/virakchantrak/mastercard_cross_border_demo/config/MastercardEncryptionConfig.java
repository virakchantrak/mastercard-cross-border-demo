package com.virakchantrak.mastercard_cross_border_demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
@RequiredArgsConstructor
public class MastercardEncryptionConfig {

    private final MastercardApiProperties properties;

    @Bean
    public X509Certificate mastercardEncryptionCertificate()
            throws CertificateException, IOException {

        try (InputStream inputStream = Files.newInputStream(
                Path.of(properties.getEncryption().getCertificatePath())
        )) {
            CertificateFactory certificateFactory =
                    CertificateFactory.getInstance("X.509");

            return (X509Certificate) certificateFactory
                    .generateCertificate(inputStream);
        }
    }

    @Bean
    public PrivateKey mastercardDecryptionPrivateKey() throws Exception {

        MastercardApiProperties.Decryption decryption =
                properties.getDecryption();

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (InputStream inputStream = Files.newInputStream(
                Path.of(decryption.getKeyStorePath())
        )) {
            char[] password = decryption.getKeyStorePassword().toCharArray();

            keyStore.load(inputStream, password);

            Key key = keyStore.getKey(
                    decryption.getKeyAlias(),
                    password
            );

            if (!(key instanceof PrivateKey privateKey)) {
                throw new IllegalStateException(
                        "Mastercard decryption key is not a PrivateKey"
                );
            }

            return privateKey;
        }
    }
}
