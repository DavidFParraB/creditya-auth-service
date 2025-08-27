package co.credit.app.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "r2dbc")
public record MysqlConnectionProperties (
    String host,
    Integer port,
    String database,
    String username,
    String password,
    // Opcional: zona horaria, ssl, etc.
    String timezone
) {}
