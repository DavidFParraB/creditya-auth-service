package co.credit.app.model.user.gateways;

import co.credit.app.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> saveUser(User user);

    Mono<User> findByDocument(String document);

    Mono<User> findByEmail(String email);

    Flux<User> getAllUsers();

    Mono<Void> deleteUser(User user);

    Mono<User> updateUser(User user);
}
