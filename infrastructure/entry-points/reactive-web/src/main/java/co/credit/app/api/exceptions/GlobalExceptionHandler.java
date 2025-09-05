package co.credit.app.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import co.credit.app.api.dto.ErrorResponseDTO;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        String friendlyMessage = "The email or document you are trying to register is already registered";
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(friendlyMessage, null);
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        String friendlyMessage = "An unexpected error occurred. Please try again later";
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(friendlyMessage, null);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            errorResponseDTO));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleGeneralException(IllegalArgumentException ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), null);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            errorResponseDTO));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleValidationError(ResponseStatusException ex) {
        log.error("Validation error occurred: {}", ex.getMessage(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), null);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO));
    }

}