package com.example.typesense.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("typesense")
@Data
public class TypesenseConfigProperties {
    private String protocol;
    private String host;
    private String apikey;
    private String port;
}
