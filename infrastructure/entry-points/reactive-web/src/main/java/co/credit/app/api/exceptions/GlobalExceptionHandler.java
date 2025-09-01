package co.credit.app.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import co.credit.app.api.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        String friendlyMessage = "The email or document you are trying to register is already registered";
        ErrorResponse errorResponse = new ErrorResponse(friendlyMessage, null);
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        String friendlyMessage = "An unexpected error occurred. Please try again later";
        ErrorResponse errorResponse = new ErrorResponse(friendlyMessage, null);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralException(IllegalArgumentException ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        String friendlyMessage = "The email or document you are trying to register is already registered";
        ErrorResponse errorResponse = new ErrorResponse(friendlyMessage, null);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationError(ResponseStatusException ex) {
        log.error("Validation error occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), null);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
    }

}