package com.iMbank.iMbank.global.component.jwt;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtTokenPropsInfo(
        String accessKey,
        Duration accessExpiration,
        String refreshKey,
        Duration refreshExpiration
) {
    @PostConstruct
    public void logProperties() {
        System.out.println("Access Key: " + accessKey);
        System.out.println("Access Expiration: " + accessExpiration);
        System.out.println("Refresh Key: " + refreshKey);
        System.out.println("Refresh Expiration: " + refreshExpiration);
    }
}
