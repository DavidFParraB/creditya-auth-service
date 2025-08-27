package co.credit.app.r2dbc;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import co.credit.app.model.user.User;
import co.credit.app.model.user.gateways.UserRepository;
import co.credit.app.r2dbc.entity.UserEntity;
import co.credit.app.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter
    extends ReactiveAdapterOperations<User, UserEntity, Long, MyReactiveRepository>
    implements UserRepository {
  public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
    /**
     * Could be use mapper.mapBuilder if your domain model implement builder pattern
     * super(repository, mapper, d ->
     * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build()); Or using mapper.map with
     * the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, User.class));
  }

  @Override
  public Mono<User> findById(Long id) {
    return repository.findById(id).map(this::toEntity);
  }

  @Override
  public Mono<Void> deleteUser(User user) {
    return repository.delete(toData(user)).then();
  }

  @Override
  public Mono<User> updateUser(User user) {
    return repository.save(toData(user)).map(this::toEntity);
  }
  @Override
  public Mono<User> findByDocument(String document) {
    return repository.findByDocument(document).map(this::toEntity);
  }
  @Override
  public Mono<User> findByEmail(String email) {
    return repository.findByEmail(email).map(this::toEntity);
  }
  
  @Override
  public Flux<User> getAllUsers() {
    return repository.findAll().map(this::toEntity);
  }

  @Override
  public Mono<Void> saveUser(User user) {
    return repository.save(toData(user)).then();
  }
}
