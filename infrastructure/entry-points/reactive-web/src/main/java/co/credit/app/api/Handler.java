package co.credit.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.commons.ValidationError;
import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.dto.ErrorResponse;
import co.credit.app.api.dto.SuccessResponse;
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
                                                                .bodyValue(new SuccessResponse(0, "OK"))))
                                .onErrorResume(ValidationError.class, e -> ServerResponse.badRequest()
                                                .bodyValue(new ErrorResponse(e.getMessage(), e.getErrors())))
                                .doOnNext(user -> log.info("User saved: {}", user));
        }

}
