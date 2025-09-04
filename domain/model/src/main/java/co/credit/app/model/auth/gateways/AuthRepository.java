package co.credit.app.model.auth.gateways;

import co.credit.app.model.auth.Auth;
import reactor.core.publisher.Mono;

public interface AuthRepository {
    Mono<Auth> generateToken(Auth auth, Long roleId);
    Mono<Boolean> validateToken(String token);
}
