package co.credit.app.jwtprovider;

import java.util.Date;
import co.credit.app.jwtprovider.config.JwtConfigProvider;
import co.credit.app.model.auth.Auth;
import co.credit.app.model.auth.gateways.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Log4j2
@RequiredArgsConstructor
@Component
public class JwtProviderAdapter implements AuthRepository {

  private final JwtConfigProvider jwtConfig;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Mono<Auth> generateToken(Auth auth, Long roleId) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtConfig.jwtExpirationTime() * 1000);

    String token = Jwts.builder()
        .claim("subject", auth.getUsername())
        .claim("role", roleId)
        .issuedAt(now)
        .expiration(validity)
        .signWith(jwtConfig.jwtSecretKey())
        .compact();

    return Mono.just(Auth.builder().token(token).expirationTime(validity.getTime()).build());
  }

  @Override
  public Mono<Auth> validateToken(String token) {
    try {
      Claims claims = Jwts.parser().verifyWith(jwtConfig.jwtSecretKey()).build()
          .parseSignedClaims(token).getPayload();

      log.info("Claims: {}", claims);
      Auth auth = Auth.builder().username(claims.get("subject", String.class))
          .expirationTime(claims.getExpiration().getTime())
          .role(claims.get("role", Long.class))
          .build();

      return Mono.just(auth);
    } catch (Exception e) {
      log.error("Invalid token: {}", e.getMessage(), e);
      return Mono.error(new IllegalArgumentException("Invalid token"));
    }
  }

  @Override
  public String encryptPassword(String password) {
    log.info("Encrypting password: {} - encrypt: {} ", password , passwordEncoder.encode(password));
    return passwordEncoder.encode(password);
  }

  @Override
  public Boolean validatePassword(String password, String encryptedPassword) {
    log.info("Validating password: {} - encrypted: {} - validate: {} ", password, encryptedPassword, passwordEncoder.matches(password, encryptedPassword));
    return passwordEncoder.matches(password, encryptedPassword);
  }

}