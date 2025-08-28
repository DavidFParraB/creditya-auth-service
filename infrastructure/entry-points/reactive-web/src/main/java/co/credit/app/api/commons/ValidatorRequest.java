package co.credit.app.api.commons;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

@Component
public class ValidatorRequest {

    private final Validator validator;

    public ValidatorRequest(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T dto) {
        return Mono.fromCallable(() -> {
            var errors = new BeanPropertyBindingResult(dto, dto.getClass().getName());
            var issuesList = validator.validate(dto);
            if (!issuesList.isEmpty()) {
                Set<String> mensajesDeError = errors.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.toSet());
                throw new ValidationError("Error Validation", mensajesDeError);
            }
            return dto;
        });
    }
}