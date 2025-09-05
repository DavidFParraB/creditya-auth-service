package co.credit.app.jwtprovider.config;

import co.credit.app.jwtprovider.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
      JwtAuthorizationFilter jwtAuthorizationFilter) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/api/login").permitAll()
            //.pathMatchers("/api/user/**").permitAll()
            .pathMatchers("/api/user").hasAnyRole("1", "2")
            .anyExchange().authenticated()
        )
        .addFilterAt(jwtAuthorizationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}