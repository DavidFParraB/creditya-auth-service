package co.credit.app.r2dbc.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;

@Configuration
public class MySQLConnectionPool {

  public static final int INITIAL_SIZE = 12;
  public static final int MAX_SIZE = 15;
  public static final int MAX_IDLE_TIME_MINUTES = 30;
  public static final int DEFAULT_PORT = 3306;

  @Bean
  public ConnectionPool mysqlConnectionPool(MysqlConnectionProperties properties) {
    MySqlConnectionConfiguration.Builder builder = MySqlConnectionConfiguration.builder()
        .host(properties.host()).port(properties.port() != null ? properties.port() : DEFAULT_PORT)
        .database(properties.database()).username(properties.username())
        .password(properties.password());

    /*
     * if (properties.timezone() != null && !properties.timezone().isBlank()) { builder =
     * builder.serverZoneId(properties.timezone()); }
     */

    MySqlConnectionConfiguration configuration = builder.build();

    ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
        .connectionFactory(MySqlConnectionFactory.from(configuration))
        .name("api-mysql-connection-pool").initialSize(INITIAL_SIZE).maxSize(MAX_SIZE)
        .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME_MINUTES)).validationQuery("SELECT 1").build();

    return new ConnectionPool(poolConfiguration);
  }
}
