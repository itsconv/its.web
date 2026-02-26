package com.itsconv.web.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {
    private final String KEY = "itsconv_rnd";

    @Bean("jasyptStringEncryptor")
    public StandardPBEStringEncryptor getEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(KEY);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        return encryptor;
    }
}
