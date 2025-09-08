package co.credit.app.redis.template.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "aux.redis")
public record ConfigProperties(
    String attemptsKey,
    Integer attemptsLimit,
    Long attemptsExpirationTime) {

}
