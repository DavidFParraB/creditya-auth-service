package co.credit.app.usecase.auth;

import co.credit.app.model.auth.Auth;
import co.credit.app.model.auth.gateways.AuthRepository;
import co.credit.app.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase {

  private final AuthRepository authService;
  private final UserRepository userRepository;

  public Mono<Auth> authenticateUser(Auth auth) {

    return userRepository.findByEmail(auth.getUsername()).flatMap(user -> {
      if (user.getPassword().equals(auth.getPassword())) {
        return authService.generateToken(auth, user.getRoleId());
      } else {
        return Mono.error(new IllegalArgumentException("Invalid email or password"));
      }
    });
  }

  public Mono<Auth> validateToken(String token) {
    return authService.validateToken(token)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid token")));
  }
}
