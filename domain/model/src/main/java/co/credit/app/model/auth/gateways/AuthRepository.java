package co.credit.app.model.auth.gateways;

import co.credit.app.model.auth.Auth;
import reactor.core.publisher.Mono;

public interface AuthRepository {

  Mono<Auth> generateToken(Auth auth, Long roleId);

  Mono<Auth> validateToken(String token);

  String encryptPassword(String password);

  Boolean validatePassword(String password, String encryptedPassword);
}
