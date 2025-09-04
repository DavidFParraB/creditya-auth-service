package co.credit.app.jwtprovider.config;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtConfigProvider jwtConfigProvider;

    @Bean
    public SecretKey jwtSecretKey() {
        // Use Keys.secretKeyFor() to generate a secure SecretKey for HS256
        return Keys.hmacShaKeyFor(jwtConfigProvider.secretKey().getBytes());
    }

    @Bean
    public long jwtExpirationTime() {
        return jwtConfigProvider.expirationTime();
    }
}