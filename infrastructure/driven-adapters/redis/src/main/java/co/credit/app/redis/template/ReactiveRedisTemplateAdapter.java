package co.credit.app.redis.template;

import co.credit.app.model.attempts.Attempts;
import co.credit.app.model.attempts.gateways.AttemptsRepository;
import co.credit.app.redis.template.config.ConfigProperties;
import co.credit.app.redis.template.entity.AttemptsEntity;
import co.credit.app.redis.template.helper.ReactiveTemplateAdapterOperations;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class ReactiveRedisTemplateAdapter extends
    ReactiveTemplateAdapterOperations<Attempts/* change for domain model */, String, AttemptsEntity/* change for adapter model */>
    implements AttemptsRepository {

  private final ConfigProperties configProperties;
  private final ReactiveRedisTemplate<String, AttemptsEntity> reactiveRedisTemplate;

  public ReactiveRedisTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory,
      ObjectMapper mapper, ConfigProperties configProperties,
      ReactiveRedisTemplate<String, AttemptsEntity> reactiveRedisTemplate) {
    /**
     *  Could be use mapper.mapBuilder if your domain model implement builder pattern
     *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
     *  Or using mapper.map with the class of the object model
     */
    super(connectionFactory, mapper,
        d -> mapper.map(d, Attempts.class/* change for domain model */));
    this.configProperties = configProperties;
    this.reactiveRedisTemplate = reactiveRedisTemplate;
  }

  @Override
  public Mono<Attempts> getAttemptsBySession(String email) {
    return reactiveRedisTemplate.opsForValue().get(configProperties.attemptsKey() + email)
        .doOnSubscribe(s -> log.info("Searching for attempts by session: {}", email))
        .doOnNext(data -> log.info("Raw data from Redis: {}", data))
        .map(this::toEntity);
  }

  @Override
  public Mono<Void> saveAttempts(String email, Attempts attempts) {
    return reactiveRedisTemplate.opsForValue()
        .set(configProperties.attemptsKey() + email, toValue(attempts),
            Duration.of(configProperties.attemptsExpirationTime(), ChronoUnit.SECONDS))
        .doOnSubscribe(s -> log.info("Saving attempts for session: {}", email))
        .then();
  }

  @Override
  public Mono<Void> deleteAttempts(String email) {
    return reactiveRedisTemplate.delete(configProperties.attemptsKey() + email)
       .doOnSubscribe(s -> log.info("Deleting attempts for session: {}", email))
       .then();
  }
}
