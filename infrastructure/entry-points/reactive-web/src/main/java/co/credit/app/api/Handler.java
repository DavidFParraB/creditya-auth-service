package co.credit.app.api;

import co.credit.app.api.dto.AuthRequestDTO;
import co.credit.app.api.mapper.AuthDTOMapper;
import co.credit.app.api.mapper.AuthRequestDTOMapper;
import co.credit.app.api.mapper.AuthResponseDTOMapper;
import co.credit.app.usecase.auth.AuthUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import co.credit.app.api.commons.ValidationError;
import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.dto.ErrorResponseDTO;
import co.credit.app.api.dto.SuccessResponseDTO;
import co.credit.app.api.dto.UserDTO;
import co.credit.app.api.mapper.UserDTOMapper;
import co.credit.app.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class Handler {

  private final UserUseCase userUseCase;
  private final UserDTOMapper userDTOMapper;
  private final ValidatorRequest validatorRequest;
  private final AuthUseCase authUseCase;
  private final AuthResponseDTOMapper authResponseDTOMapper;
  private final AuthRequestDTOMapper authRequestDTOMapper;


  public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
    return userUseCase.getAllUsers()
        .map(userDTOMapper::toResponse)
        .collectList()
        .flatMap(userDTOs -> ServerResponse.ok().bodyValue(userDTOs))
        .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UserDTO.class)
        .flatMap(validatorRequest::validate)
        .flatMap(userDTO -> userUseCase.saveUser(userDTOMapper.toModel(userDTO))
            .then(ServerResponse.status(HttpStatus.OK)
                .bodyValue(new SuccessResponseDTO(0, "OK"))))
        .onErrorResume(ValidationError.class, e -> ServerResponse.badRequest()
            .bodyValue(new ErrorResponseDTO(e.getMessage(), e.getErrors())))
        .doOnNext(user -> log.info("User saved: {}", user));
  }

  public Mono<ServerResponse> listenGETByDocumentUseCase(ServerRequest serverRequest) {
    String document = serverRequest.pathVariable("document");

    return userUseCase.getUserByDocument(document)
        .map(userDTOMapper::toResponse)
        .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto))
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
            "User not found with param: " + document)));
  }

  public Mono<ServerResponse> listenGETByEmailUseCase(ServerRequest serverRequest) {
    String email = serverRequest.pathVariable("email");

    return userUseCase.getUserByEmail(email)
        .map(userDTOMapper::toResponse)
        .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto))
        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
            "User not found with param: " + email)));
  }

  public Mono<ServerResponse> listenPOSTLogin(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(AuthRequestDTO.class)
        .flatMap(authREquestDTO -> authUseCase.authenticateUser(
            authRequestDTOMapper.toModel(authREquestDTO)))
        .flatMap(
            jwtToken -> ServerResponse.ok().bodyValue(authResponseDTOMapper.toResponse(jwtToken)))
        .switchIfEmpty(Mono.error(
            new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")));
  }
}
