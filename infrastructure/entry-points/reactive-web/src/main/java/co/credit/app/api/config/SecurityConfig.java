package co.credit.app.api.config;

import co.credit.app.api.config.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
      AuthorizationFilter authorizationFilter) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/api/login").permitAll()
            .pathMatchers("/api/user/find-by-document/**").permitAll()
            .pathMatchers("/api/user/find-by-email/**").permitAll()
            .pathMatchers(HttpMethod.GET, "/api/user").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/user").hasAnyRole("1", "3")
            .anyExchange().authenticated()
        )
        .addFilterAt(authorizationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}