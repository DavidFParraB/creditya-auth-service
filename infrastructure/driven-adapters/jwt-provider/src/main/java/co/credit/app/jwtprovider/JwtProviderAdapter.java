package co.credit.app.jwtprovider;

import java.util.Date;
import co.credit.app.jwtprovider.config.JwtConfig;
import co.credit.app.model.auth.Auth;
import co.credit.app.model.auth.gateways.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Log4j2
@RequiredArgsConstructor
@Component
public class JwtProviderAdapter implements AuthRepository {

  private final JwtConfig jwtConfig;

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
  public Mono<Boolean> validateToken(String token) {
    try {
      /*Claims claims = Jwts.parser().setSigningKey(jwtConfig.jwtSecretKey()).build()
          .parseClaimsJws(token).getBody();*/
      Claims claims = Jwts.parser().verifyWith(jwtConfig.jwtSecretKey()).build()
          .parseSignedClaims(token).getPayload();
      log.info("Token validated: {}", claims);
      return Mono.just(true);
    } catch (Exception e) {
      return Mono.just(false);
    }
  }
}