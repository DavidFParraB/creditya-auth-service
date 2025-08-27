package co.credit.app.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import co.credit.app.r2dbc.entity.UserEntity;
import reactor.core.publisher.Mono;
@Repository
public interface MyReactiveRepository
    extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

        Mono<UserEntity> findByDocument(String document);
        Mono<UserEntity> findByEmail(String email);

}
