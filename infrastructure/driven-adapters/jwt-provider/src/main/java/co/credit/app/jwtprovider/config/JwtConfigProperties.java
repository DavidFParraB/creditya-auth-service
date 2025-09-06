package co.credit.app.jwtprovider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfigProperties(
    String secretKey,
    Long expirationTime
) {

}