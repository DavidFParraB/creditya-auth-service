package co.credit.app.redis.template.config;

import co.credit.app.redis.template.entity.AttemptsEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  @Primary
  public ReactiveRedisTemplate<String, AttemptsEntity> personaRedisTemplate(
      ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<AttemptsEntity> serializer = new Jackson2JsonRedisSerializer<>(
        AttemptsEntity.class);
    RedisSerializationContext.RedisSerializationContextBuilder<String, AttemptsEntity> builder =
        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
    RedisSerializationContext<String, AttemptsEntity> context = builder.value(serializer).build();
    return new ReactiveRedisTemplate<>(factory, context);
  }
}