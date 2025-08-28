package co.credit.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.commons.ValidatorRequest;
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
    // private final jakarta.validation.Validator validator;

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
                /*
                 * .doOnNext(userDTO -> {
                 * log.error("Validando datos {}", userDTO.toString());
                 * var issues = validator.validate(userDTO);
                 * if (!issues.isEmpty()) {
                 * throw new WebExchangeBindException(null, null); // Lanza la excepción si
                 * falla
                 * }
                 * })
                 */
                .flatMap(userDTO -> userUseCase.saveUser(userDTOMapper.toModel(userDTO))
                        .then(ServerResponse.status(HttpStatus.OK).build()))
                .onErrorResume(e -> {
                    log.error("Error saving user: {}", e.getMessage());
                    return ServerResponse.badRequest().bodyValue("Errores de validación: " + e.getMessage());
                })
                .doOnNext(user -> log.info("User saved: {}", user));
    }

}
