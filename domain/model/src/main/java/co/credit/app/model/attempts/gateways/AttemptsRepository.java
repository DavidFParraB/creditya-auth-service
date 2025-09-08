package co.credit.app.model.attempts.gateways;

import co.credit.app.model.attempts.Attempts;
import reactor.core.publisher.Mono;

public interface AttemptsRepository {
  Mono<Void> saveAttempts(String email, Attempts attempts);
  Mono<Attempts> getAttemptsBySession(String email);
  Mono<Void> deleteAttempts(String email);
}
