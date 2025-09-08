
package co.credit.app.usecase.user;

import co.credit.app.model.auth.gateways.AuthRepository;
import co.credit.app.model.user.User;
import co.credit.app.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

  private final UserRepository userRepository;
  private final AuthRepository authRepository;

  public Mono<User> saveUser(User user) {

    user.setPassword(authRepository.encryptPassword(user.getPassword()));

    return this.getUserByEmail(user.getEmail())
        .flatMap(existingUser -> Mono.<User>error(new IllegalArgumentException(
                "User with email: " + existingUser.getEmail() + " already exists."))
        )
        .switchIfEmpty(Mono.defer(() -> userRepository.saveUser(user)));
  }

  public Flux<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  public Mono<User> getUserByDocument(String document) {
    return userRepository.findByDocument(document);
  }

  public Mono<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Mono<Void> deleteUser(User user) {
    return userRepository.deleteUser(user).then();
  }

  public Mono<User> updateUser(User user) {
    return userRepository.updateUser(user);
  }

}
