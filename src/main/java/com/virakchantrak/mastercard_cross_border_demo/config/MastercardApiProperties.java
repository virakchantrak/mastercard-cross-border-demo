package com.virakchantrak.mastercard_cross_border_demo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mastercard.api")
public class MastercardApiProperties {

    private String baseUrl;

    private String partnerId;

    private Encryption encryption = new Encryption();
    private Decryption decryption = new Decryption();

    @Getter
    @Setter
    public static class Encryption {
        private boolean enabled;
        private String certificatePath;
        private String fingerprint;
    }

    @Getter
    @Setter
    public static class Decryption {
        private String keyStorePath;
        private String keyAlias;
        private String keyStorePassword;
    }
}

