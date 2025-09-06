package co.credit.app.jwtprovider.config;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfigProvider {

  private final JwtConfigProperties jwtConfigProperties;

  @Bean
  public SecretKey jwtSecretKey() {
    return Keys.hmacShaKeyFor(jwtConfigProperties.secretKey().getBytes());
  }

  @Bean
  public long jwtExpirationTime() {
    return jwtConfigProperties.expirationTime();
  }
}