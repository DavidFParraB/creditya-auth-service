package co.credit.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.dto.UserDTO;
import co.credit.app.api.mapper.UserDTOMapper;
import co.credit.app.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;
    private final UserDTOMapper userDTOMapper;


    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return userUseCase.getAllUsers()
                .map(userDTOMapper::toResponse)
                .collectList()
                .flatMap(userDTOs -> ServerResponse.ok().bodyValue(userDTOs))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
       return serverRequest.bodyToMono(UserDTO.class)
       .flatMap(userDTO -> userUseCase.saveUser(userDTOMapper.toModel(userDTO))
       .then(ServerResponse.status(HttpStatus.OK).build())
       .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

}
