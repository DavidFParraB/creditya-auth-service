
package co.credit.app.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.dto.ErrorResponse;
import co.credit.app.api.dto.SuccessResponse;
import co.credit.app.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(path = "/api/user", produces = {
                    MediaType.APPLICATION_JSON_VALUE }, method = org.springframework.web.bind.annotation.RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETUseCase", operation = @Operation(operationId = "getUsers", tags = {
                            "Users" }, summary = "Get all users", description = "Retrieve a list of all users", responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            })),
            @RouterOperation(path = "/api/user", produces = {
                    MediaType.APPLICATION_JSON_VALUE }, method = org.springframework.web.bind.annotation.RequestMethod.POST, beanClass = Handler.class, beanMethod = "listenPOSTUseCase", operation = @Operation(operationId = "createUser", tags = {
                            "Users" }, summary = "Create a new user", description = "Create a new user with the provided details", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true, content = @Content(schema = @Schema(implementation = UserDTO.class))), responses = {
                                    @ApiResponse(responseCode = "200", description = "User created", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }))
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/user"), handler::listenGETUseCase)
                .andRoute(POST("/api/user"), handler::listenPOSTUseCase);
    }
}
