package co.credit.app.api.config.filter;

import co.credit.app.usecase.auth.AuthUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthorizationFilter implements WebFilter {

  private final AuthUseCase authUseCase;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      log.info("Authorization header: {}", authHeader);
      String token = authHeader.substring(7);

      return authUseCase.validateToken(token)
          .flatMap(authResult -> {
            String username = authResult.getUsername();
            String role = authResult.getRole().toString();

            var auth = new UsernamePasswordAuthenticationToken(username, null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role)));
            log.info("Authentication successful for user: {} - role: {}", username, role);
            return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
          })
          .onErrorResume(e -> {
            log.error("Token validation failed: {}", e.getMessage());
            return chain.filter(exchange);
          });
    }
    return chain.filter(exchange);
  }
}