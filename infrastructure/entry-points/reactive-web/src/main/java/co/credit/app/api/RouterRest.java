
package co.credit.app.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.dto.ErrorResponseDTO;
import co.credit.app.api.dto.SuccessResponseDTO;
import co.credit.app.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class RouterRest {

  @RouterOperations({
      @RouterOperation(path = "/api/user", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETUseCase", operation = @Operation(operationId = "getUsers", tags = {
          "Users"}, summary = "Get all users", description = "Retrieve a list of all users", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
          @ApiResponse(responseCode = "500", description = "Internal server error")
      })),
      @RouterOperation(path = "/api/user", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.POST, beanClass = Handler.class, beanMethod = "listenPOSTUseCase", operation = @Operation(operationId = "createUser", tags = {
          "Users"}, summary = "Create a new user", description = "Create a new user with the provided details", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true, content = @Content(schema = @Schema(implementation = UserDTO.class))), responses = {
          @ApiResponse(responseCode = "200", description = "User created", content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error")
      })),
      @RouterOperation(path = "/api/user/find-by-email/{email}", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETByEmailUseCase", operation = @Operation(operationId = "getUserByEmail", tags = {
          "Users"}, summary = "Get user by email", description = "Retrieve a user by email", parameters = {
          @Parameter(in = ParameterIn.PATH, name = "email", description = "User email", required = true, schema = @Schema(type = "string"))
      }, responses = {
          @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
      })),
      @RouterOperation(path = "/api/user/find-by-document/{document}", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETByDocumentUseCase", operation = @Operation(operationId = "getUserByDocument", tags = {
          "Users"}, summary = "Get user by document", description = "Retrieve a user by document", parameters = {
          @Parameter(in = ParameterIn.PATH, name = "document", description = "User document", required = true, schema = @Schema(type = "string"))
      }, responses = {
          @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
      }))
  })
  @Bean
  public RouterFunction<ServerResponse> routerFunction(Handler handler) {
    return route(GET("/api/user"), handler::listenGETUseCase)
        .andRoute(POST("/api/user"), handler::listenPOSTUseCase)
        .andRoute(GET("/api/user/find-by-email/{email}"), handler::listenGETByEmailUseCase)
        .andRoute(GET("/api/user/find-by-document/{document}"),
            handler::listenGETByDocumentUseCase)
        .andRoute(POST("/api/login"), handler::listenPOSTLogin);
  }
}
